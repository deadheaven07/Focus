package com.focusflow.domain.usecase

import com.focusflow.domain.model.Habit
import com.focusflow.domain.repository.HabitRepository
import javax.inject.Inject

class ToggleHabitCompletionUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit, date: Long): Result<Unit> = repository.toggleHabitCompletion(habit, date)
}
