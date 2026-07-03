package com.focusflow.domain.usecase

import com.focusflow.domain.model.Habit
import com.focusflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitsUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    operator fun invoke(): Flow<List<Habit>> = repository.getHabits()
}
