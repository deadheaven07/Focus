package com.focusflow.presentation.habits

import com.focusflow.domain.model.Habit

data class HabitListState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
