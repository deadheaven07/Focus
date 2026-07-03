package com.focusflow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.focusflow.data.local.dao.FocusSessionDao
import com.focusflow.data.local.dao.HabitDao
import com.focusflow.data.local.entity.FocusSessionEntity
import com.focusflow.data.local.entity.HabitCompletionEntity
import com.focusflow.data.local.entity.HabitEntity

@Database(
    entities = [HabitEntity::class, HabitCompletionEntity::class, FocusSessionEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FocusFlowDatabase : RoomDatabase() {
    abstract val habitDao: HabitDao
    abstract val focusSessionDao: FocusSessionDao
}
