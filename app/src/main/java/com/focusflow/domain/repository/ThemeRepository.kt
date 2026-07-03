package com.focusflow.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val isDarkMode: Flow<Boolean?>
    suspend fun setDarkMode(isDarkMode: Boolean?)
}
