package com.hoc081098.datastoresample.domain

class ChangeShowCompleted(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(showCompleted: Boolean) =
        userPreferencesRepository.updateShowCompleted(showCompleted)
}