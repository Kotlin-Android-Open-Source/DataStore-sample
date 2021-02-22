package com.hoc081098.datastoresample.domain.repo

import com.hoc081098.datastoresample.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun tasks(): Flow<List<Task>>
}