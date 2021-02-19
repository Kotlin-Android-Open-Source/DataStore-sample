package com.hoc081098.datastoresample.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hoc081098.datastoresample.domain.ChangeShowCompleted
import com.hoc081098.datastoresample.domain.FilterSortTasks
import com.hoc081098.datastoresample.domain.FilteredSortedTasks
import com.hoc081098.datastoresample.domain.SortOrder
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    filterSortTasks: FilterSortTasks,
    private val _changeShowCompleted: ChangeShowCompleted,
) : ViewModel() {
    private val _state = mutableStateOf(
        FilteredSortedTasks(
            tasks = emptyList(),
            showCompleted = true,
            sortOrder = SortOrder.NONE
        )
    )

    val state: State<FilteredSortedTasks> get() = _state

    init {
        Log.d("MainViewModel", "$this::init")

        filterSortTasks()
            .onEach { _state.value = it }
            .catch { }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "$this::onCleared")
    }

    fun changeShowCompleted(showCompleted: Boolean) {
        viewModelScope.launch {
            _changeShowCompleted(showCompleted)
        }
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