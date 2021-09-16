package com.cource.student.persistence

import com.cource.student.domain.StudentOutbox
import com.cource.student.domain.StudentOutboxRepository
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
class StudentOutboxReactiveRepository(
    private val databaseClient: DatabaseClient,
    private val mapper: StudentOutboxMapper
): StudentOutboxRepository {
    private val clazz = StudentOutboxEntity::class.java

    override fun create(studentOutbox: StudentOutbox): Mono<StudentOutbox> {
        return databaseClient
            .insert()
            .into(clazz)
            .using(mapper.map(studentOutbox))
            .then()
            .thenReturn(studentOutbox)
    }

    override fun delete(studentOutbox: StudentOutbox): Mono<StudentOutbox> {
       return databaseClient
            .delete()
            .from(clazz)
            .matching(Criteria.where("id").`is`(studentOutbox.id))
            .fetch()
            .rowsUpdated()
           .thenReturn(studentOutbox)
    }

    override fun findById(id: UUID): Mono<StudentOutbox> {
        return databaseClient
            .select()
            .from(clazz)
            .matching(Criteria.where("id").`is`(id))
            .fetch()
            .one()
            .map { mapper.map(it) }
    }

    override fun findNext(): Mono<StudentOutbox> {
        return databaseClient
            .execute("SELECT * FROM student_outbox LIMIT 1 FOR UPDATE SKIP LOCKED")
            .`as`(clazz)
            .fetch()
            .first()
            .map { mapper.map(it) }
    }
}