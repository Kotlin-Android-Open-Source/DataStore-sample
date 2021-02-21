package com.hoc081098.datastoresample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoc081098.datastoresample.R
import com.hoc081098.datastoresample.domain.FilteredSortedTasks
import com.hoc081098.datastoresample.domain.Task
import com.hoc081098.datastoresample.ui.theme.DataStoreSampleTheme

@Composable
fun MainScreen(
    state: FilteredSortedTasks?,
    changeShowCompleted: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Jetpack DataStore sample")
                }
            )
        }
    ) {
        if (state == null) {
            CircularProgressIndicator()
        } else {
            Column {
                MainTasksList(state.tasks)

                Row(modifier = Modifier.padding(all = 32.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
                        contentDescription = null,
                        modifier = Modifier.preferredSize(24.dp),
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Checkbox(
                        checked = state.showCompleted,
                        onCheckedChange = { changeShowCompleted(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainTasksList(tasks: List<Task>) {
    LazyColumn(
//        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(all = 8.dp)
    ) {
        items(tasks) { task ->
            TaskRow(task = task)
            Divider()
        }
    }
}

@Composable
fun TaskRow(task: Task) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(all = 12.dp)
    ) {
        Text(
            text = task.name,
            style = MaterialTheme.typography.subtitle1,
        )
        Text(
            text = "Priority: ${task.priority.name}",
            style = MaterialTheme.typography.subtitle2,
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    DataStoreSampleTheme {
        MainScreen(
            null,
            {},
        )
    }
}
