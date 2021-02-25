package com.hoc081098.datastoresample.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hoc081098.datastoresample.domain.model.SortOrder
import com.hoc081098.datastoresample.domain.model.SortOrder.*
import com.hoc081098.datastoresample.domain.model.Theme
import com.hoc081098.datastoresample.domain.model.UserPreferences
import com.hoc081098.datastoresample.domain.repo.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : UserPreferencesRepository {

    private object Keys {
        val showCompleted = booleanPreferencesKey("show_completed")
        val sortOrder = stringPreferencesKey("sort_order")
        val theme = booleanPreferencesKey("theme")
    }

    private inline val Preferences.showCompleted
        get() = this[Keys.showCompleted] ?: false

    private inline val Preferences.sortOrder
        get() = this[Keys.sortOrder]?.let(SortOrder::valueOf) ?: NONE

    override val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch {
            // throws an IOException when an error is encountered when reading data
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            UserPreferences(
                showCompleted = preferences.showCompleted,
                sortOrder = preferences.sortOrder,
            )
        }
        .distinctUntilChanged()

    override suspend fun enableSortByDeadline(enabled: Boolean) {
        dataStore.edit {
            val sortOrder = it.sortOrder

            val newSortOrder = if (enabled) {
                when (sortOrder) {
                    NONE -> BY_DEADLINE
                    BY_DEADLINE -> BY_DEADLINE
                    BY_PRIORITY -> BY_DEADLINE_AND_PRIORITY
                    BY_DEADLINE_AND_PRIORITY -> BY_DEADLINE
                }
            } else {
                when (sortOrder) {
                    NONE -> NONE
                    BY_DEADLINE -> NONE
                    BY_PRIORITY -> NONE
                    BY_DEADLINE_AND_PRIORITY -> BY_PRIORITY
                }
            }

            it[Keys.sortOrder] = newSortOrder.name
        }
    }

    override suspend fun enableSortByPriority(enabled: Boolean) {
        dataStore.edit {
            val sortOrder = it.sortOrder

            val newSortOrder = if (enabled) {
                when (sortOrder) {
                    NONE -> BY_PRIORITY
                    BY_DEADLINE -> BY_DEADLINE_AND_PRIORITY
                    BY_PRIORITY -> BY_PRIORITY
                    BY_DEADLINE_AND_PRIORITY -> BY_PRIORITY
                }
            } else {
                when (sortOrder) {
                    NONE -> NONE
                    BY_DEADLINE -> NONE
                    BY_PRIORITY -> NONE
                    BY_DEADLINE_AND_PRIORITY -> BY_DEADLINE
                }
            }

            it[Keys.sortOrder] = newSortOrder.name
        }
    }

    override suspend fun updateShowCompleted(showCompleted: Boolean) {
        dataStore.edit { it[Keys.showCompleted] = showCompleted }
        Log.d("UserPreferencesRepo", "updateShowCompleted $showCompleted")
    }

    override val theme: Flow<Theme> = dataStore.data
        .catch {
            // throws an IOException when an error is encountered when reading data
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            when (it[Keys.theme]) {
                true -> Theme.NIGHT_YES
                false -> Theme.NIGHT_NO
                null -> Theme.NIGHT_UNSPECIFIED
            }
        }
        .distinctUntilChanged()

    override suspend fun changeTheme(lightTheme: Boolean) {
        dataStore.edit { it[Keys.theme] = lightTheme }
        Log.d("UserPreferencesRepo", "changeTheme $lightTheme")
    }
}