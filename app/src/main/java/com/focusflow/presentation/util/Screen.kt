package com.focusflow.presentation.util

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object HabitList : Screen("habit_list")
    object AddHabit : Screen("add_habit")
    object FocusSession : Screen("focus_session")
    object FocusHistory : Screen("focus_history")
    object Login : Screen("login")
    object Profile : Screen("profile")
}
