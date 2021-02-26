package com.hoc081098.datastoresample.domain.usecase

import com.hoc081098.datastoresample.domain.model.Theme
import com.hoc081098.datastoresample.domain.repo.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetTheme(private val userPreferencesRepository: UserPreferencesRepository) {
    operator fun invoke(): Flow<Theme> = userPreferencesRepository.theme
}