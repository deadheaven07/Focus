package com.focusflow.domain.model

data class FocusSession(
    val id: Long = 0,
    val type: FocusSessionType,
    val durationMillis: Long,
    val startTime: Long,
    val endTime: Long,
    val isCompleted: Boolean
)
