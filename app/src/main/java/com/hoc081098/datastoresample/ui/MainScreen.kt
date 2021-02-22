package com.hoc081098.datastoresample.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoc081098.datastoresample.domain.model.FilteredSortedTasks
import com.hoc081098.datastoresample.domain.model.SortOrder
import com.hoc081098.datastoresample.domain.model.Task
import com.hoc081098.datastoresample.ui.theme.DataStoreSampleTheme

@Composable
fun MainScreen(
    state: FilteredSortedTasks?,
    changeShowCompleted: (Boolean) -> Unit,
    enableSortByDeadline: (Boolean) -> Unit,
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
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            ) {
                MainTasksList(state.tasks, Modifier.weight(1f))

                Row(modifier = Modifier.padding(all = 16.dp)) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = null,
                        tint = LocalContentColor.current.copy(alpha = 0.5f),
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = "Show completed tasks",
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Checkbox(
                        checked = state.showCompleted,
                        onCheckedChange = changeShowCompleted
                    )
                }

                Row(modifier = Modifier.padding(all = 16.dp)) {
                    Icon(
                        imageVector = Icons.Default.Reorder,
                        contentDescription = null,
                        tint = LocalContentColor.current.copy(alpha = 0.5f),
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    SortChip(
                        text = "Priority",
                        selected = state.sortOrder == SortOrder.BY_PRIORITY
                                || state.sortOrder == SortOrder.BY_DEADLINE_AND_PRIORITY,
                        setSelected = {

                        },
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    SortChip(
                        text = "Deadline",
                        selected = state.sortOrder == SortOrder.BY_DEADLINE
                                || state.sortOrder == SortOrder.BY_DEADLINE_AND_PRIORITY,
                        setSelected = enableSortByDeadline,
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MainTasksList(tasks: List<Task>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 8.dp)
    ) {
        items(tasks) { task ->
            TaskRow(task = task)
            Divider(
                thickness = 0.7.dp
            )
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

@Composable
fun SortChip(
    text: String,
    selected: Boolean,
    setSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Surface(
        modifier = modifier.preferredHeight(28.dp),
        color = MaterialTheme.colors.secondary,
        shape = shape,
        elevation = 2.dp
    ) {
        Box(
            modifier = Modifier.toggleable(
                value = selected,
                onValueChange = setSelected,
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 6.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.width(animateDpAsState(targetValue = if (selected) 24.dp else 0.dp).value),
                )

                Spacer(modifier = Modifier.width(animateDpAsState(targetValue = if (selected) 2.dp else 0.dp).value))

                Text(
                    text = text,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,

                    )
            }

        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    DataStoreSampleTheme {
        MainScreen(
            null,
            {},
            {},
        )
    }
}