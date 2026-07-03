package com.focusflow.core.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * wrapper for Firebase Analytics so we don't have tracking code all over the place.
 */
@Singleton
class AnalyticsHelper @Inject constructor(
    @ApplicationContext context: Context
) {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logEvent(name: String, params: Bundle? = null) {
        firebaseAnalytics.logEvent(name, params)
    }

    fun logHabitCreated(habitName: String, category: String) {
        logEvent("habit_created", Bundle().apply {
            putString("habit_name", habitName)
            putString("category", category)
        })
    }

    fun logHabitCompleted(habitName: String) {
        logEvent("habit_completed", Bundle().apply {
            putString("habit_name", habitName)
        })
    }

    fun logFocusStarted(sessionType: String) {
        logEvent("focus_started", Bundle().apply {
            putString("session_type", sessionType)
        })
    }
}
