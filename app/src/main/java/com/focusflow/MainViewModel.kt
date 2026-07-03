package com.focusflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusflow.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {
    val currentUser = authRepository.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)
}
