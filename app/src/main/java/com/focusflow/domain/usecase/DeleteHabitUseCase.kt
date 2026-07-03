package com.focusflow.domain.usecase

import com.focusflow.domain.model.Habit
import com.focusflow.domain.repository.HabitRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit): Result<Unit> {
        return repository.deleteHabit(habit)
    }
}
