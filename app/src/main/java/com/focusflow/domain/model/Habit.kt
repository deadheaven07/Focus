package com.focusflow.domain.model

import java.time.LocalDate

data class Habit(
    val id: Long = 0,
    val name: String,
    val description: String,
    val category: HabitCategory,
    val frequency: HabitFrequency,
    val createdAt: Long = System.currentTimeMillis(),
    val isCompletedToday: Boolean = false,
    val streakCount: Int = 0
)

enum class HabitFrequency {
    DAILY,
    WEEKLY
}
