package com.focusflow.core.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.focusflow.data.local.dao.HabitDao
import com.focusflow.core.util.DateUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import androidx.hilt.work.HiltWorker
import kotlinx.coroutines.flow.first

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitDao: HabitDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val habitId = inputData.getLong("habit_id", -1L)
        val habitName = inputData.getString("habit_name") ?: "a habit"
        
        if (habitId != -1L) {
            val today = DateUtils.getStartOfDay()
            val completions = habitDao.getCompletionsByDate(today).first()
            val isCompleted = completions.any { it.habitId == habitId }
            
            if (isCompleted) return Result.success()
        }
        
        showNotification(
            "Time for your habit!",
            "Don't forget to complete: $habitName"
        )
        
        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(applicationContext, "reminders_channel")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        try {
            NotificationManagerCompat.from(applicationContext).notify(System.currentTimeMillis().toInt(), notification)
        } catch (e: SecurityException) {
            // Handle permission not granted
        }
    }
}
