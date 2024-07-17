package com.example.demo.infra.db

import com.example.demo.domain.Todo
import com.example.demo.domain.TodoRepository
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

@Entity
@Table(name = "todos")
data class TodoEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val isDone: Boolean,
) {
    constructor(v: Todo) : this(
        id = v.id,
        name = v.name,
        isDone = v.isDone,
    )

    fun toDomain() = Todo(
        id,
        name,
        isDone
    )
}

fun Todo.toEntity() = TodoEntity(this)

interface TodoDbRepo : CrudRepository<TodoEntity, UUID> {
    @Query("select t from TodoEntity t where t.id = ?1")
    fun find(id: UUID): TodoEntity?
}

@Component
class TodoRepositoryImpl(
    private val db: TodoDbRepo
) : TodoRepository {
    override fun findById(id: UUID): Todo? {
        return db.find(id)?.toDomain()
    }

    override fun insert(v: Todo) {
        db.save(v.toEntity())
    }

    override fun update(v: Todo): Todo {
        db.save(v.toEntity())
        return v
    }

    override fun delete(id: UUID) {
        db.find(id)?.apply { db.delete(this) }
    }
}
