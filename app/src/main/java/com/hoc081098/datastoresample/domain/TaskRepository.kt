package com.hoc081098.datastoresample.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun tasks(): Flow<List<Task>>
}