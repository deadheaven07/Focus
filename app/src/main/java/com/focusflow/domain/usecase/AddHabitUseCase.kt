package com.focusflow.domain.usecase

import com.focusflow.core.notification.ReminderManager
import com.focusflow.domain.model.Habit
import com.focusflow.domain.repository.HabitRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(
    private val repository: HabitRepository,
    private val reminderManager: ReminderManager
) {
    suspend operator fun invoke(habit: Habit): Result<Unit> {
        val result = repository.insertHabit(habit)
        
        return result.map { habitId ->
            // if we saved it okay, set up a default reminder for 8 AM
            reminderManager.scheduleHabitReminder(habitId, habit.name, 8, 0)
        }
    }
}
