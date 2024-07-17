package com.example.demo.web.controllers

import com.example.demo.domain.Todo
import com.example.demo.usecase.UseCase
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@WebMvcTest(TodoController::class)
class TodoControllerTest(
    private val mvc: MockMvc,
    @MockkBean private val useCase: UseCase
) : FunSpec({
    extensions(SpringExtension)

    test("createTodo") {
        val stubTodo = Todo(name = "test")
        every {
            useCase.createTodo(any())
        } returns stubTodo
        val response = mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"name": "test"}""")
        ).andReturn().response
        val want = jacksonObjectMapper().writeValueAsString(stubTodo)
        response.status shouldBe HttpStatus.CREATED.value()
        response.contentAsString shouldBe want
    }
})
