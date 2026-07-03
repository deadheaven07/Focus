package com.focusflow.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusflow.domain.repository.AuthRepository
import com.focusflow.domain.repository.FocusRepository
import com.focusflow.domain.repository.HabitRepository
import com.focusflow.domain.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val habitRepository: HabitRepository,
    private val focusRepository: FocusRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {
    
    val currentUser = authRepository.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isDarkMode = themeRepository.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val profileState = combine(
        habitRepository.getHabits(),
        focusRepository.getFocusHistory()
    ) { habits, focusSessions ->
        ProfileStats(
            totalHabits = habits.size,
            totalFocusSessions = focusSessions.size,
            completedToday = habits.count { it.isCompletedToday }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileStats())

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun toggleTheme(isDark: Boolean?) {
        viewModelScope.launch {
            themeRepository.setDarkMode(isDark)
        }
    }
}

data class ProfileStats(
    val totalHabits: Int = 0,
    val totalFocusSessions: Int = 0,
    val completedToday: Int = 0
)
