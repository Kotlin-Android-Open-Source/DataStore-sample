package com.hoc081098.datastoresample.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.hoc081098.datastoresample.domain.model.SortOrder
import com.hoc081098.datastoresample.domain.model.Task
import com.hoc081098.datastoresample.domain.model.TaskPriority
import com.hoc081098.datastoresample.domain.model.TaskPriority.*
import com.hoc081098.datastoresample.domain.usecase.FilteredSortedTasks
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen(
    state: FilteredSortedTasks?,
    changeShowCompleted: (Boolean) -> Unit,
    enableSortByDeadline: (Boolean) -> Unit,
    enableSortByPriority: (Boolean) -> Unit,
    lightTheme: Boolean,
    changeTheme: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Jetpack DataStore sample")
                },
                actions = {
                    IconToggleButton(
                        checked = lightTheme,
                        onCheckedChange = changeTheme,
                        modifier = Modifier.semantics {
                            // Use a custom click label that accessibility services can communicate to the user.
                            // We only want to override the label, not the actual action, so for the action we pass null.
                            onClick(
                                label = if (lightTheme) "To dark mode" else "To night mode",
                                action = null
                            )
                        }
                    ) {
                        Icon(
                            imageVector = if (lightTheme) {
                                Icons.Filled.LightMode
                            } else {
                                Icons.Outlined.LightMode
                            },
                            contentDescription = null,  // handled by click label of parent
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
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
                MainTasksList(state.tasks,
                    Modifier
                        .weight(1f)
                        .padding(innerPadding))

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
                        setSelected = enableSortByPriority,
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
    ) {
        itemsIndexed(tasks) { index, task ->
            TaskRow(task = task)
            if (index < tasks.lastIndex) {
                Divider(
                    thickness = 0.7.dp
                )
            }
        }
    }
}

private val TaskPriority.color: Color
    get() {
        return when (this) {
            HIGH -> Color.Red.copy(alpha = 0.8f)
            MEDIUM -> Color.Magenta.copy(alpha = 0.8f)
            LOW -> Color.Green.copy(alpha = 0.8f)
        }
    }
private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)

@Composable
fun TaskRow(task: Task) {
    val bgColor = if (task.completed) {
        LocalContentColor.current.copy(alpha = 0.07f)
    } else {
        Color.Unspecified
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(all = 12.dp)
    ) {
        Text(
            text = task.name,
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.requiredHeight(8.dp))
        Text(
            text = "Priority ${task.priority.name}",
            style = MaterialTheme.typography.subtitle2.copy(
                color = task.priority.color
            ),
        )
        Spacer(modifier = Modifier.requiredHeight(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = LocalContentColor.current.copy(alpha = 0.5f),
            )
            Spacer(modifier = Modifier.requiredWidth(8.dp))
            Text(
                dateFormat.format(task.deadline),
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SortChip(
    text: String,
    selected: Boolean,
    setSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Surface(
        modifier = modifier.height(28.dp),
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
                AnimatedVisibility(visible = selected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.width(24.dp),
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }

                Text(
                    text = text,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,

                    )
            }

        }
    }
}

//@Preview
//@Composable
//fun MainScreenPreview() {
//    DataStoreSampleTheme {
//        MainScreen(
//            state = null,
//            changeShowCompleted = {},
//            enableSortByDeadline = {},
//            enableSortByPriority = {},
//            lightTheme = false,
//            changeTheme = {}
//        )
//    }
//}
//
//@Preview
//@Composable
//fun TaskRowPreview() {
//    TaskRow(
//        task = Task(
//            name = "Complete graduate project",
//            deadline = Date(),
//            priority = HIGH,
//        ),
//    )
//}