package com.example.simpleapplication

import org.junit.Assert.assertEquals
import org.junit.Test

class TaskViewModelTest {
    @Test
    fun deleteTask_removesOnlyTaskWithMatchingId() {
        val viewModel = TaskViewModel()
        viewModel.onTitleChange("Tugas 1")
        viewModel.onCourseChange("Matematika")
        viewModel.addTask()
        viewModel.onTitleChange("Tugas 2")
        viewModel.onCourseChange("Fisika")
        viewModel.addTask()

        viewModel.deleteTask(1)

        assertEquals(listOf(2), viewModel.uiState.value.tasks.map { it.id })
        assertEquals("Tugas 2", viewModel.uiState.value.tasks.single().title)
    }

    @Test
    fun deleteTask_whenLastTaskIsRemoved_marksListEmpty() {
        val viewModel = TaskViewModel()
        viewModel.onTitleChange("Tugas")
        viewModel.onCourseChange("Kimia")
        viewModel.addTask()

        viewModel.deleteTask(1)

        assertEquals(true, viewModel.uiState.value.isEmpty)
    }
}
