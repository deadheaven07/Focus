package com.focusflow.domain.model

enum class FocusSessionType(val displayName: String, val defaultDurationMinutes: Int) {
    POMODORO("Pomodoro", 25),
    BREAK("Break", 5),
    DEEP_WORK("Deep Work", 30),
    MEDITATION("Silent Meditation", 30),
    STUDY("Study Session", 50),
    CUSTOM("Custom", 0)
}
