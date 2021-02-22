package com.hoc081098.datastoresample.domain.repo

import com.hoc081098.datastoresample.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferences>

    suspend fun enableSortByDeadline(enabled: Boolean)

    suspend fun enableSortByPriority(enabled: Boolean)

    suspend fun updateShowCompleted(showCompleted: Boolean)
}