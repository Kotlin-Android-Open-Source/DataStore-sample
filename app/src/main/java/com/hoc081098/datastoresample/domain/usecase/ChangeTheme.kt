package com.hoc081098.datastoresample.domain.usecase

import com.hoc081098.datastoresample.domain.repo.UserPreferencesRepository

class ChangeTheme(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend operator fun invoke(lightTheme: Boolean) =
        userPreferencesRepository.changeTheme(lightTheme)
}