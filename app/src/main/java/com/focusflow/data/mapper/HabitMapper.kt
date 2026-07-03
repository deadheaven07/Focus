package com.focusflow.data.mapper

import com.focusflow.data.local.entity.HabitEntity
import com.focusflow.domain.model.Habit

fun HabitEntity.toDomain(isCompletedToday: Boolean): Habit {
    return Habit(
        id = id,
        name = name,
        description = description,
        category = category,
        frequency = frequency,
        createdAt = createdAt,
        isCompletedToday = isCompletedToday,
        streakCount = streakCount
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        description = description,
        category = category,
        frequency = frequency,
        createdAt = createdAt,
        streakCount = streakCount
    )
}
