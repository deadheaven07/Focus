package com.focusflow.core.util

import android.util.Patterns

object InputValidator {

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // Firebase requires at least 6, but we'll recommend at least 8 for better security
        return password.length >= 6
    }
}
