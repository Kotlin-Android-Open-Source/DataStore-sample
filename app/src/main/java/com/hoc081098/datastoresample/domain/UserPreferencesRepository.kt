package com.hoc081098.datastoresample.domain

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferences>

    suspend fun enableSortByDeadline(enabled: Boolean)

    suspend fun enableSortByPriority(enabled: Boolean)

    suspend fun updateShowCompleted(showCompleted: Boolean)
}