package com.example.simpleapplication


import androidx.lifecycle.ViewModel
import com.example.simpleapplication.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {
    // MutableStateFlow privat, hanya ViewModel yang boleh mengubah
    private val _uiState = MutableStateFlow(TaskUiState())
    // StateFlow read-only untuk UI
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private var nextId = 1

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(titleInput = value, titleError = null) }
    }

    fun onCourseChange(value: String) {
        _uiState.update { it.copy(courseInput = value, courseError = null) }
    }

    /** Fitur 1: tambah tugas (dengan validasi input). */
    fun addTask() {
        val current = _uiState.value
        val title = current.titleInput.trim()
        val course = current.courseInput.trim()

        val titleError = if (title.isEmpty()) "Judul tugas tidak boleh kosong" else null
        val courseError = if (course.isEmpty()) "Mata kuliah tidak boleh kosong" else null

        if (titleError != null || courseError != null) {
            _uiState.update { it.copy(titleError = titleError, courseError = courseError) }
            return
        }

        val newTask = Task(id = nextId++, title = title, course = course)
        _uiState.update {
            it.copy(
                tasks = it.tasks + newTask,
                titleInput = "",
                courseInput = "",
                titleError = null,
                courseError = null
            )
        }
    }

    /** Fitur 2: tandai tugas selesai / belum selesai. */
    fun toggleTask(id: Int) {
        _uiState.update { state ->
            state.copy(tasks = state.tasks.map {
                if (it.id == id) it.copy(isDone = !it.isDone) else it
            })
        }
    }

    /** Hapus tugas berdasarkan ID. */
    fun deleteTask(id: Int) {
        _uiState.update { state ->
            state.copy(tasks = state.tasks.filterNot { it.id == id })
        }
    }
}