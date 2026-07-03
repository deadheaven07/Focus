package com.focusflow.domain.repository

import com.focusflow.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    suspend fun insertHabit(habit: Habit): Result<Long>
    suspend fun deleteHabit(habit: Habit): Result<Unit>
    suspend fun toggleHabitCompletion(habit: Habit, date: Long): Result<Unit>
}
