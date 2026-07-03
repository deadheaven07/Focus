package com.focusflow.data.repository

import com.focusflow.core.util.DateUtils
import com.focusflow.data.local.dao.HabitDao
import com.focusflow.data.local.entity.HabitCompletionEntity
import com.focusflow.data.mapper.toDomain
import com.focusflow.data.mapper.toEntity
import com.focusflow.domain.model.Habit
import com.focusflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * handles all the habit data logic. 
 * we use Room for local storage and a simple custom streak calculation.
 */
class HabitRepositoryImpl @Inject constructor(
    private val dao: HabitDao
) : HabitRepository {

    override fun getHabits(): Flow<List<Habit>> {
        val today = DateUtils.getStartOfDay()
        return combine(
            dao.getAllHabits(),
            dao.getCompletionsByDate(today)
        ) { habits, completions ->
            val completedTodayIds = completions.map { it.habitId }.toSet()
            habits.map { it.toDomain(completedTodayIds.contains(it.id)) }
        }
    }

    override suspend fun insertHabit(habit: Habit): Result<Long> {
        return try {
            val id = dao.insertHabit(habit.toEntity())
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteHabit(habit: Habit): Result<Unit> {
        return try {
            dao.deleteHabit(habit.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleHabitCompletion(habit: Habit, date: Long): Result<Unit> {
        return try {
            val targetDate = DateUtils.getStartOfDay(date)
            val completions = dao.getCompletionsForHabit(habit.id).first()
            val existing = completions.find { it.date == targetDate }

            if (existing != null) {
                dao.deleteCompletion(existing)
            } else {
                dao.insertCompletion(HabitCompletionEntity(habit.id, targetDate))
            }
            
            // refresh streak after toggling
            recalculateStreak(habit.id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun recalculateStreak(habitId: Long) {
        val habit = dao.getHabitById(habitId) ?: return
        val completions = dao.getCompletionsForHabit(habitId).first()
            .sortedByDescending { it.date }
        
        var count = 0
        var checkDate = DateUtils.getStartOfDay()
        
        // count backwards from today
        for (c in completions) {
            if (c.date == checkDate) {
                count++
                checkDate -= 24 * 60 * 60 * 1000L // go back 1 day
            } else if (c.date < checkDate) {
                // gap found, streak broken
                break
            }
        }
        
        dao.updateHabit(habit.copy(streakCount = count))
    }
}
