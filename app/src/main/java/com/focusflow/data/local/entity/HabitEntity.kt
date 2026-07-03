package com.focusflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.focusflow.domain.model.HabitCategory
import com.focusflow.domain.model.HabitFrequency

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val category: HabitCategory,
    val frequency: HabitFrequency,
    val createdAt: Long,
    val streakCount: Int = 0
)
