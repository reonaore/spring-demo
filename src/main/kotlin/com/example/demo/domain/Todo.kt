package com.example.demo.domain

import java.util.*

data class Todo(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val isDone: Boolean = false,
)

interface TodoRepository {
    fun findById(id: UUID): Todo?
    fun insert(v: Todo)
    fun update(v: Todo): Todo
    fun delete(id: UUID)
}
