package com.example.demo.web.controllers

import com.example.demo.domain.Todo
import com.example.demo.usecase.UseCase
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
class TodoController(
    private val useCase: UseCase
) {
    @PostMapping("/api/v1/todos")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(
        @RequestBody request: CreateTodoRequest
    ): Todo {
        return useCase.createTodo(
            Todo(
                name = request.name,
            )
        )
    }
}

data class CreateTodoRequest(
    @NotNull
    @Size(min = 1, max = 255)
    val name: String
)
