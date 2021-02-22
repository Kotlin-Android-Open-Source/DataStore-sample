package com.hoc081098.datastoresample.domain.model

import com.hoc081098.datastoresample.domain.model.SortOrder

data class UserPreferences(
    val showCompleted: Boolean,
    val sortOrder: SortOrder,
)