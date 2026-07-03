package com.focusflow.domain.repository

import com.focusflow.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow

interface FocusRepository {
    fun getFocusHistory(): Flow<List<FocusSession>>
    suspend fun saveSession(session: FocusSession)
}
