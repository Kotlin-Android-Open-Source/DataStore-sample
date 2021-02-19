package com.hoc081098.datastoresample

import android.app.Application
import androidx.datastore.preferences.createDataStore
import com.hoc081098.datastoresample.data.TaskRepositoryImpl
import com.hoc081098.datastoresample.data.UserPreferencesRepositoryImpl
import com.hoc081098.datastoresample.domain.ChangeShowCompleted
import com.hoc081098.datastoresample.domain.FilterSortTasks
import com.hoc081098.datastoresample.ui.MainViewModel

object Locator {
    private var application: Application? = null

    private inline val requireApplication
        get() = application ?: error("Missing call: initWith(application)")

    fun initWith(application: Application) {
        this.application = application
    }

    val mainViewModelFactory get() = MainViewModel.Factory(filterSortTasks, changeShowCompleted)

    private val filterSortTasks
        get() = FilterSortTasks(
            taskRepository = taskRepository,
            userPreferencesRepository = userPreferencesRepository
        )

    private val changeShowCompleted get() = ChangeShowCompleted(userPreferencesRepository)

    private val taskRepository by lazy { TaskRepositoryImpl() }
    private val userPreferencesRepository by lazy {
        val dataStore = requireApplication.createDataStore(name = "user_preferences")
        UserPreferencesRepositoryImpl(dataStore)
    }
}