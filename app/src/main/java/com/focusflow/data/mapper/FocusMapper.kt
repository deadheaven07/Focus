package com.focusflow.data.mapper

import com.focusflow.data.local.entity.FocusSessionEntity
import com.focusflow.domain.model.FocusSession

fun FocusSessionEntity.toDomain(): FocusSession {
    return FocusSession(
        id = id,
        type = type,
        durationMillis = durationMillis,
        startTime = startTime,
        endTime = endTime,
        isCompleted = isCompleted
    )
}

fun FocusSession.toEntity(): FocusSessionEntity {
    return FocusSessionEntity(
        id = id,
        type = type,
        durationMillis = durationMillis,
        startTime = startTime,
        endTime = endTime,
        isCompleted = isCompleted
    )
}
