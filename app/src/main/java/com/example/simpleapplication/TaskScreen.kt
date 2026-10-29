package com.example.simpleapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpleapplication.Task
import com.example.simpleapplication.TaskUiState
import com.example.simpleapplication.TaskViewModel

/** Stateful: terhubung ke ViewModel. */
@Composable
fun TaskScreen(viewModel: TaskViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskContent(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onCourseChange = viewModel::onCourseChange,
        onAddTask = viewModel::addTask,
        onToggleTask = viewModel::toggleTask,
        onDeleteTask = viewModel::deleteTask
    )
}

/** Stateless: hanya menampilkan state dan meneruskan event. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    uiState: TaskUiState,
    onTitleChange: (String) -> Unit,
    onCourseChange: (String) -> Unit,
    onAddTask: () -> Unit,
    onToggleTask: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Catatan Tugas Kuliah") }) }
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Adaptive layout: layar sempit = bertumpuk, layar lebar = berdampingan
            if (maxWidth < 600.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TaskForm(uiState, onTitleChange, onCourseChange, onAddTask)
                    TaskList(
                        tasks = uiState.tasks,
                        isEmpty = uiState.isEmpty,
                        onToggleTask = onToggleTask,
                        onDeleteTask = onDeleteTask,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    TaskForm(
                        uiState, onTitleChange, onCourseChange, onAddTask,
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    )
                    TaskList(
                        tasks = uiState.tasks,
                        isEmpty = uiState.isEmpty,
                        onToggleTask = onToggleTask,
                        onDeleteTask = onDeleteTask,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskForm(
    uiState: TaskUiState,
    onTitleChange: (String) -> Unit,
    onCourseChange: (String) -> Unit,
    onAddTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = uiState.titleInput,
            onValueChange = onTitleChange,
            label = { Text("Judul tugas") },
            isError = uiState.titleError != null,
            supportingText = { uiState.titleError?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.courseInput,
            onValueChange = onCourseChange,
            label = { Text("Mata kuliah") },
            isError = uiState.courseError != null,
            supportingText = { uiState.courseError?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onAddTask, modifier = Modifier.fillMaxWidth()) {
            Text("Tambah Tugas")
        }
    }
}

@Composable
private fun TaskList(
    tasks: List<Task>,
    isEmpty: Boolean,
    onToggleTask: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isEmpty) {
        // Kondisi data kosong
        Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = "Belum ada tugas. Tambahkan tugas pertamamu!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        // Kondisi data tersedia
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskItem(task, onToggleTask, onDeleteTask)
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    onToggle: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.isDone, onCheckedChange = { onToggle(task.id) })
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else null
                )
                Text(text = task.course, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(task.id) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Hapus tugas ${task.title}"
                )
            }
        }
    }
}