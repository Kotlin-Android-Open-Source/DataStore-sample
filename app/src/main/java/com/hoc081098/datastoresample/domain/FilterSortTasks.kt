package com.hoc081098.datastoresample.domain.model

import com.hoc081098.datastoresample.domain.repo.TaskRepository
import com.hoc081098.datastoresample.domain.repo.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

data class FilteredSortedTasks(
    val tasks: List<Task>,
    val showCompleted: Boolean,
    val sortOrder: SortOrder,
)

class FilterSortTasks(
    private val taskRepository: TaskRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    operator fun invoke(): Flow<FilteredSortedTasks> {
        return combine(
            taskRepository.tasks(),
            userPreferencesRepository.userPreferences,
        ) { tasks, (showCompleted, sortOrder) ->
            val filtered = if (showCompleted) tasks.filter { it.completed } else tasks

            FilteredSortedTasks(
                tasks = filtered.sortedWith(sortOrder.comparator),
                showCompleted = showCompleted,
                sortOrder = sortOrder
            )
        }
    }
}

