package com.focusflow.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "habit_completions",
    primaryKeys = ["habitId", "date"],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("habitId")]
)
data class HabitCompletionEntity(
    val habitId: Long,
    val date: Long // Timestamp representing the date (e.g., midnight)
)
