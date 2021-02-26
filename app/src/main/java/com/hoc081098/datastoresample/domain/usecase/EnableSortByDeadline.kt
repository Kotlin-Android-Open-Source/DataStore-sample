package com.hoc081098.datastoresample.domain.usecase

import com.hoc081098.datastoresample.domain.repo.UserPreferencesRepository

class EnableSortByDeadline(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(enabled: Boolean) =
        userPreferencesRepository.enableSortByDeadline(enabled)
}