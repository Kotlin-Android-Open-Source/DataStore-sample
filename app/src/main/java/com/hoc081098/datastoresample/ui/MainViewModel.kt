package com.hoc081098.datastoresample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hoc081098.datastoresample.domain.usecase.ChangeShowCompleted
import com.hoc081098.datastoresample.domain.usecase.ChangeTheme
import com.hoc081098.datastoresample.domain.usecase.EnableSortByDeadline
import com.hoc081098.datastoresample.domain.usecase.EnableSortByPriority
import com.hoc081098.datastoresample.domain.usecase.GetTheme
import com.hoc081098.datastoresample.domain.usecase.FilterSortTasks
import com.hoc081098.datastoresample.domain.usecase.FilteredSortedTasks
import com.hoc081098.datastoresample.domain.model.Theme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    filterSortTasks: FilterSortTasks,
    getTheme: GetTheme,
    private val _changeShowCompleted: ChangeShowCompleted,
    private val _enableSortByDeadline: EnableSortByDeadline,
    private val _enableSortByPriority: EnableSortByPriority,
    private val _changeTheme: ChangeTheme,
) : ViewModel() {
    val state: StateFlow<FilteredSortedTasks?> = filterSortTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null,
        )

    val theme: StateFlow<Theme?> = getTheme().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null,
    )

    init {
        Log.d("MainViewModel", "$this::init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "$this::onCleared")
    }

    fun changeShowCompleted(showCompleted: Boolean) {
        viewModelScope.launch { _changeShowCompleted(showCompleted) }
    }

    fun enableSortByDeadline(enabled: Boolean) {
        viewModelScope.launch { _enableSortByDeadline(enabled) }
    }

    fun enableSortByPriority(enabled: Boolean) {
        viewModelScope.launch { _enableSortByPriority(enabled) }
    }

    fun changeTheme(lightTheme: Boolean) {
        viewModelScope.launch { _changeTheme(lightTheme) }
    }

    class Factory(
        private val filterSortTasks: FilterSortTasks,
        private val getTheme: GetTheme,
        private val changeShowCompleted: ChangeShowCompleted,
        private val enableSortByDeadline: EnableSortByDeadline,
        private val enableSortByPriority: EnableSortByPriority,
        private val changeTheme: ChangeTheme,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    filterSortTasks = filterSortTasks,
                    getTheme = getTheme,
                    _changeShowCompleted = changeShowCompleted,
                    _enableSortByDeadline = enableSortByDeadline,
                    _enableSortByPriority = enableSortByPriority,
                    _changeTheme = changeTheme,
                ) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}