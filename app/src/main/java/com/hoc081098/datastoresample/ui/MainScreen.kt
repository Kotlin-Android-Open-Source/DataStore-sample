package com.hoc081098.datastoresample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoc081098.datastoresample.Locator
import com.hoc081098.datastoresample.R
import com.hoc081098.datastoresample.domain.Task
import com.hoc081098.datastoresample.ui.theme.DataStoreSampleTheme

@Composable
fun MainScreen() {
    val viewModel = viewModel<MainViewModel>(factory = Locator.mainViewModelFactory)
    val state by viewModel.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Jetpack DataStore sample")
                }
            )
        }
    ) {
        Column {
//            MainTasksList(state.tasks)

            Row(modifier = Modifier.padding(all = 32.dp).wraps) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
                    contentDescription = null,
                    modifier = Modifier.preferredSize(24.dp),
                )

                Spacer(modifier = Modifier.width(16.dp))

                Checkbox(
                    checked = state.showCompleted,
                    onCheckedChange = { viewModel.changeShowCompleted(it) }
                )
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
/*  val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
  fun parseDate(date: String? = null): Date = date?.let(simpleDateFormat::parse) ?: Date()
  val tasks = listOf(
      Task(
          name = "Complete graduate project",
          deadline = parseDate("25-12-2020"),
          priority = TaskPriority.HIGH,
      ),
      Task(
          name = "Learning Jetpack Compose",
          deadline = parseDate("01-01-2021"),
          priority = TaskPriority.MEDIUM,
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
      ),
      Task(
          name = "Understand how to migrate to DataStore",
          deadline = parseDate(),
          priority = TaskPriority.HIGH,
      ),
      Task(
          name = "Complete graduate project",
          deadline = parseDate("25-12-2020"),
          priority = TaskPriority.HIGH,
      ),
      Task(
          name = "Learning Jetpack Compose",
          deadline = parseDate("01-01-2021"),
          priority = TaskPriority.MEDIUM,
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
      ),
      Task(
          name = "Understand how to migrate to DataStore",
          deadline = parseDate(),
          priority = TaskPriority.HIGH,
      )
  )*/

    DataStoreSampleTheme {
        MainScreen()
    }
}
