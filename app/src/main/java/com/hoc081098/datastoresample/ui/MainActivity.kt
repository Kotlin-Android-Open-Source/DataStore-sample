package com.hoc081098.datastoresample.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hoc081098.datastoresample.Locator
import com.hoc081098.datastoresample.domain.model.Theme
import com.hoc081098.datastoresample.ui.theme.DataStoreSampleTheme

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>(factoryProducer = { Locator.mainViewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val theme by viewModel.theme.collectAsState()

            val darkTheme = when (theme) {
                Theme.NIGHT_YES -> false
                Theme.NIGHT_NO -> true
                Theme.NIGHT_UNSPECIFIED -> isSystemInDarkTheme()
                null -> return@setContent
            }

            DataStoreSampleTheme(darkTheme = darkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val state by viewModel.state.collectAsState()

                    MainScreen(
                        state = state,
                        changeShowCompleted = viewModel::changeShowCompleted,
                        enableSortByDeadline = viewModel::enableSortByDeadline,
                        lightTheme = !darkTheme,
                        changeTheme = viewModel::changeTheme,
                    )
                }
            }
        }
    }
}
