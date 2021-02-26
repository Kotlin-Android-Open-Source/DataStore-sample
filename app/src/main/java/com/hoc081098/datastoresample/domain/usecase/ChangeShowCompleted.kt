package com.hoc081098.datastoresample.domain.usecase

import com.hoc081098.datastoresample.domain.repo.UserPreferencesRepository

class ChangeShowCompleted(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(showCompleted: Boolean) =
        userPreferencesRepository.updateShowCompleted(showCompleted)
}