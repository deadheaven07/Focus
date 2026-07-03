package com.focusflow.core.timer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.focusflow.data.local.dao.FocusSessionDao
import com.focusflow.data.local.entity.FocusSessionEntity
import com.focusflow.domain.model.FocusSessionType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Foreground service to keep the timer ticking even if the user leaves the app.
 * Using a Service is much more reliable than just a ViewModel for Pomodoro timers.
 */
@AndroidEntryPoint
class FocusTimerService : Service() {

    @Inject
    lateinit var focusSessionDao: FocusSessionDao

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val binder = TimerBinder()
    private var timer: CountDownTimer? = null
    
    // UI can observe these to stay in sync with the background timer
    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft = _timeLeft.asStateFlow()
    
    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private var totalDuration = 0L
    private var currentType: FocusSessionType? = null
    private var startTime = 0L

    companion object {
        const val CHANNEL_ID = "focus_timer_channel"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        setupNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // keep us alive if possible
    }

    fun startTimer(durationMillis: Long, type: FocusSessionType) {
        totalDuration = durationMillis
        currentType = type
        startTime = System.currentTimeMillis()
        _timeLeft.value = durationMillis
        _isRunning.value = true
        
        timer?.cancel()
        timer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished
                refreshNotification(millisUntilFinished)
            }

            override fun onFinish() {
                saveFinishedSession()
                _timeLeft.value = 0
                _isRunning.value = false
                stopMe()
            }
        }.start()
        
        startForeground(NOTIFICATION_ID, buildNotification(durationMillis))
    }

    private fun saveFinishedSession() {
        val type = currentType ?: return
        val endTime = System.currentTimeMillis()
        serviceScope.launch {
            focusSessionDao.insertSession(
                FocusSessionEntity(
                    type = type,
                    durationMillis = totalDuration, // they finished the whole thing
                    startTime = startTime,
                    endTime = endTime,
                    isCompleted = true
                )
            )
        }
    }

    fun pauseTimer() {
        timer?.cancel()
        _isRunning.value = false
        // Update notification to show it's paused
        refreshNotification(_timeLeft.value)
    }

    fun resumeTimer() {
        if (_timeLeft.value > 0) {
            val remaining = _timeLeft.value
            _isRunning.value = true
            timer = object : CountDownTimer(remaining, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    _timeLeft.value = millisUntilFinished
                    refreshNotification(millisUntilFinished)
                }
                override fun onFinish() {
                    saveFinishedSession()
                    _timeLeft.value = 0
                    _isRunning.value = false
                    stopMe()
                }
            }.start()
        }
    }

    fun stopTimer() {
        // save what they've done so far even if they stopped early
        val type = currentType
        if (type != null && _timeLeft.value < totalDuration) {
            val endTime = System.currentTimeMillis()
            val worked = totalDuration - _timeLeft.value
            serviceScope.launch {
                focusSessionDao.insertSession(
                    FocusSessionEntity(
                        type = type,
                        durationMillis = worked,
                        startTime = startTime,
                        endTime = endTime,
                        isCompleted = false
                    )
                )
            }
        }
        
        timer?.cancel()
        _timeLeft.value = 0
        _isRunning.value = false
        stopMe()
    }

    private fun stopMe() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Focus Timer",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(timeLeftMillis: Long): Notification {
        val mins = (timeLeftMillis / 1000) / 60
        val secs = (timeLeftMillis / 1000) % 60
        val display = String.format("%02d:%02d", mins, secs)
        
        val status = if (_isRunning.value) "Active" else "Paused"
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Focus Session $status")
            .setContentText("Remaining: $display")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .setOnlyAlertOnce(true) // don't beep every second
            .build()
    }

    private fun refreshNotification(timeLeftMillis: Long) {
        val notification = buildNotification(timeLeftMillis)
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder = binder

    inner class TimerBinder : Binder() {
        fun getService(): FocusTimerService = this@FocusTimerService
    }
}
