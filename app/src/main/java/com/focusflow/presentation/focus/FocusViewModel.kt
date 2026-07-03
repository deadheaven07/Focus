package com.focusflow.presentation.focus

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusflow.core.timer.FocusTimerService
import com.focusflow.core.util.AnalyticsHelper
import com.focusflow.domain.model.FocusSessionType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    // we connect to the foreground service to keep the timer running in the background
    private val _timerService = MutableStateFlow<FocusTimerService?>(null)
    
    // observe time and running state from the service
    val timeLeft = _timerService.flatMapLatest { it?.timeLeft ?: flowOf(0L) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)
    
    val isRunning = _timerService.flatMapLatest { it?.isRunning ?: flowOf(false) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as FocusTimerService.TimerBinder
            _timerService.value = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            _timerService.value = null
        }
    }

    init {
        // bind to the service as soon as the ViewModel is created
        Intent(context, FocusTimerService::class.java).also { intent ->
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun startFocusSession(type: FocusSessionType) {
        _timerService.value?.startTimer(type.defaultDurationMinutes * 60 * 1000L, type)
        analyticsHelper.logFocusStarted(type.displayName)
    }

    fun pauseResume() {
        val service = _timerService.value ?: return
        if (service.isRunning.value) {
            service.pauseTimer()
        } else {
            service.resumeTimer()
        }
    }

    fun stopSession() {
        _timerService.value?.stopTimer()
    }

    override fun onCleared() {
        super.onCleared()
        // clean up the connection when we're done
        context.unbindService(serviceConnection)
    }
}
