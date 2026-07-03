package com.focusflow.presentation.dashboard

import com.focusflow.domain.model.Habit
import com.focusflow.domain.model.HabitInsight

data class DashboardState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = false,
    val totalStreak: Int = 0,
    val insights: List<HabitInsight> = emptyList()
) {
    val completedHabits = habits.filter { it.isCompletedToday }
    val pendingHabits = habits.filter { !it.isCompletedToday }
    val progress: Float = if (habits.isNotEmpty()) completedHabits.size.toFloat() / habits.size else 0f
}
