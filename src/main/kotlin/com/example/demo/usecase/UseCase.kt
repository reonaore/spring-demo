package com.example.demo.usecase

import com.example.demo.domain.Todo
import com.example.demo.domain.TodoRepository
import org.springframework.stereotype.Service
import java.util.*

interface UseCase {
    fun findById(id: UUID): Todo?
    fun createTodo(v: Todo): Todo
    fun updateTodo(v: Todo): Todo
    fun deleteTodo(id: UUID)
}

@Service
class UseCaseImpl(
    private val repo: TodoRepository
) : UseCase {
    override fun findById(id: UUID): Todo? {
        return repo.findById(id)
    }

    override fun createTodo(v: Todo): Todo {
        repo.insert(v)
        return v
    }

    override fun updateTodo(v: Todo): Todo {
        repo.update(v)
        return v
    }

    override fun deleteTodo(id: UUID) {
        repo.delete(id)
    }

}
