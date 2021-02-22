package com.hoc081098.datastoresample.domain.model

enum class SortOrder {
    NONE,
    BY_DEADLINE,
    BY_PRIORITY,
    BY_DEADLINE_AND_PRIORITY;

    val comparator: Comparator<Task> by lazy {
        when (this) {
            NONE -> Comparator { _, _ -> 0 }
            BY_DEADLINE -> compareByDescending { it.deadline }
            BY_PRIORITY -> compareBy { it.priority }
            BY_DEADLINE_AND_PRIORITY -> compareByDescending<Task> { it.deadline }.thenBy { it.priority }
        }
    }
}