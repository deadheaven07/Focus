package com.focusflow.data.local.dao

import androidx.room.*
import com.focusflow.data.local.entity.HabitCompletionEntity
import com.focusflow.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getHabitById(id: Long): HabitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: HabitCompletionEntity)

    @Delete
    suspend fun deleteCompletion(completion: HabitCompletionEntity)

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId")
    fun getCompletionsForHabit(habitId: Long): Flow<List<HabitCompletionEntity>>

    @Query("SELECT * FROM habit_completions WHERE date = :date")
    fun getCompletionsByDate(date: Long): Flow<List<HabitCompletionEntity>>
}
