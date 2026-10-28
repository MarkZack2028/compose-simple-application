package com.example.simpleapplication

// membuat logika program
class TaskRepository {

    // menampung data object Task
    private val tasks = mutableListOf<Task>()

    // penghitung ID, harus var supaya bisa dinaikkan
    private var nextId = 1

    fun getAll(): List<Task> {
        return tasks.toList()
    }

    // tambah tugas (sekarang menerima course juga)
    fun add(title: String, course: String) {
        tasks.add(Task(id = nextId++, title = title, course = course))
    }

    // fitur hapus: bagian teman Anda
    fun delete(id: Int) {
        tasks.removeAll { it.id == id }
    }
}