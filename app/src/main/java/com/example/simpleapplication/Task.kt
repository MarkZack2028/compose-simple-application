package com.example.simpleapplication


// data class untuk menyimpan data
data class Task (
    val id: Int,
    val title: String,
    val course: String,
    val isDone: Boolean = false
)