package com.focusflow.core.notification

import android.content.Context
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * simple wrapper to handle all our WorkManager scheduling.
 */
@Singleton
class ReminderManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleHabitReminder(habitId: Long, habitName: String, hour: Int, minute: Int) {
        val input = workDataOf(
            "habit_id" to habitId,
            "habit_name" to habitName
        )

        val dailyRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInputData(input)
            .setInitialDelay(calculateDelayUntil(hour, minute), TimeUnit.MILLISECONDS)
            .addTag("habit_$habitId")
            .build()

        // we use UPDATE so if they change the time, it just overwrites the old one
        workManager.enqueueUniquePeriodicWork(
            "habit_$habitId",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyRequest
        )
    }

    private fun calculateDelayUntil(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.before(now)) {
            // if we missed it today, schedule for tomorrow
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        return target.timeInMillis - now.timeInMillis
    }
}
