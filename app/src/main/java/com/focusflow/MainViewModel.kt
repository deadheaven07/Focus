package com.focusflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusflow.domain.repository.AuthRepository
import com.focusflow.domain.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    authRepository: AuthRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {
    val currentUser = authRepository.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)

    val isDarkMode = themeRepository.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)

    fun toggleTheme(isDark: Boolean?) {
        viewModelScope.launch {
            themeRepository.setDarkMode(isDark)
        }
    }
}
