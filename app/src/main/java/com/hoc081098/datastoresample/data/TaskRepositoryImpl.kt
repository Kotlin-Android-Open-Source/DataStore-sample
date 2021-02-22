package com.hoc081098.datastoresample.data

import com.hoc081098.datastoresample.domain.model.Task
import com.hoc081098.datastoresample.domain.model.TaskPriority
import com.hoc081098.datastoresample.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.*

class TaskRepositoryImpl : TaskRepository {

    private val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private fun parseDate(date: String? = null): Date = date?.let(simpleDateFormat::parse) ?: Date()

    override fun tasks(): Flow<List<Task>> {
        return flowOf(
            listOf(
                Task(
                    name = "Complete graduate project",
                    deadline = parseDate("25-12-2020"),
                    priority = TaskPriority.HIGH,
                ),
                Task(
                    name = "Learning Jetpack Compose",
                    deadline = parseDate("01-01-2021"),
                    priority = TaskPriority.MEDIUM,
                    completed = true,
                ),
                Task(
                    name = "Learning NestJs",
                    deadline = parseDate("02-01-2021"),
                    priority = TaskPriority.LOW,
                ),
                Task(
                    name = "Learn about Polymer",
                    deadline = parseDate("10-10-2020"),
                    priority = TaskPriority.LOW,
                    completed = true,
                ),
                Task(
                    name = "Learning Functional programming with Λrrow",
                    deadline = parseDate("01-01-2022"),
                    priority = TaskPriority.MEDIUM,
                ),
                Task(
                    name = "Learning Functional programming with Bow Swift",
                    deadline = parseDate("01-01-2022"),
                    priority = TaskPriority.MEDIUM,
                    completed = true,
                ),
                Task(
                    name = "Understand how to migrate to DataStore",
                    deadline = parseDate(),
                    priority = TaskPriority.HIGH,
                ),
                Task(
                    name = "Compose is awesome",
                    deadline = parseDate(),
                    priority = TaskPriority.HIGH,
                    completed = true,
                ),
                Task(
                    name = "RxDart",
                    deadline = parseDate("25-12-2020"),
                    priority = TaskPriority.HIGH,
                ),
                Task(
                    name = "RxSwift",
                    deadline = parseDate("01-01-2021"),
                    priority = TaskPriority.MEDIUM,
                ),
            )
        )
    }
}