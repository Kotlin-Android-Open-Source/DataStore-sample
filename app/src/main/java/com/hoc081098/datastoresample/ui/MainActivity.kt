package com.hoc081098.datastoresample.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoc081098.datastoresample.Locator
import com.hoc081098.datastoresample.ui.theme.DataStoreSampleTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DataStoreSampleTheme(true) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val viewModel = viewModel<MainViewModel>(factory = Locator.mainViewModelFactory)
                    val state by viewModel.state.collectAsState()

                    MainScreen(
                        state,
                        viewModel::changeShowCompleted,
                        viewModel::enableSortByDeadline,
                    )
                }
            }
        }
    }
}
