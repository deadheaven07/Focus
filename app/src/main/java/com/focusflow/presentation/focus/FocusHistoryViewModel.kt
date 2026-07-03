package com.focusflow.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusflow.data.local.dao.FocusSessionDao
import com.focusflow.data.local.entity.FocusSessionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FocusHistoryViewModel @Inject constructor(
    private val focusSessionDao: FocusSessionDao
) : ViewModel() {

    val sessions: StateFlow<List<FocusSessionEntity>> = focusSessionDao.getAllSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
