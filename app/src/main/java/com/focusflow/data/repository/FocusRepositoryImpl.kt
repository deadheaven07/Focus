package com.focusflow.data.repository

import com.focusflow.data.local.dao.FocusSessionDao
import com.focusflow.data.mapper.toDomain
import com.focusflow.data.mapper.toEntity
import com.focusflow.domain.model.FocusSession
import com.focusflow.domain.repository.FocusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FocusRepositoryImpl @Inject constructor(
    private val dao: FocusSessionDao
) : FocusRepository {

    override fun getFocusHistory(): Flow<List<FocusSession>> {
        return dao.getAllSessions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveSession(session: FocusSession) {
        dao.insertSession(session.toEntity())
    }
}
