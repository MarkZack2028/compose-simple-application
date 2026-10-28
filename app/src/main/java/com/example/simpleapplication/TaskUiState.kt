package com.example.simpleapplication

import com.example.simpleapplication.Task
/** Seluruh state layar dalam satu data class. */
data class TaskUiState(
    val tasks: List<Task> = emptyList(),   // kosong -> tampilkan pesan data kosong
    val titleInput: String = "",
    val courseInput: String = "",
    val titleError: String? = null,        // pesan validasi
    val courseError: String? = null
) {
    val isEmpty: Boolean get() = tasks.isEmpty()
}