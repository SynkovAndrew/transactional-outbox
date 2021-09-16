package com.cource.student.persistence

import com.cource.student.domain.Student
import com.cource.student.domain.StudentRepository
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
class StudentReactiveRepository(
    private val databaseClient: DatabaseClient,
    private val mapper: StudentMapper
): StudentRepository {
    private val clazz = StudentEntity::class.java

    override fun create(student: Student): Mono<Student> {
        return databaseClient
            .insert()
            .into(clazz)
            .using(mapper.map(student))
            .then()
            .thenReturn(student)
    }

    override fun update(student: Student): Mono<Student> {
        return databaseClient
            .update()
            .table(clazz)
            .using(mapper.map(student))
            .matching(Criteria.where("id").`is`(student.id))
            .fetch()
            .rowsUpdated()
            .thenReturn(student)
    }

    override fun findById(id: UUID): Mono<Student> {
        return databaseClient
            .select()
            .from(clazz)
            .matching(Criteria.where("id").`is`(id))
            .fetch()
            .one()
            .map { mapper.map(it) }
    }
}