package com.focusflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.focusflow.domain.model.FocusSessionType

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: FocusSessionType,
    val durationMillis: Long,
    val startTime: Long,
    val endTime: Long,
    val isCompleted: Boolean
)
