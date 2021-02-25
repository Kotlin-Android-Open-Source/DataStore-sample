package com.hoc081098.datastoresample.domain

import com.hoc081098.datastoresample.domain.repo.UserPreferencesRepository

class EnableSortByPriority(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend operator fun invoke(enabled: Boolean) =
        userPreferencesRepository.enableSortByPriority(enabled)
}