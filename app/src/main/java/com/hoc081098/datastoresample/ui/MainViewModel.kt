package com.hoc081098.datastoresample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hoc081098.datastoresample.domain.ChangeShowCompleted
import com.hoc081098.datastoresample.domain.FilterSortTasks
import com.hoc081098.datastoresample.domain.FilteredSortedTasks
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    filterSortTasks: FilterSortTasks,
    private val _changeShowCompleted: ChangeShowCompleted,
) : ViewModel() {
    val state: StateFlow<FilteredSortedTasks?> = filterSortTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null as FilteredSortedTasks?,
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

    class Factory(
        private val filterSortTasks: FilterSortTasks,
        private val changeShowCompleted: ChangeShowCompleted,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    filterSortTasks,
                    changeShowCompleted
                ) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}