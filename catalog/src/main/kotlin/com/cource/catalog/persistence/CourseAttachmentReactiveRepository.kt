package com.cource.catalog.persistence

import com.cource.catalog.domain.CourseAttachment
import com.cource.catalog.domain.CourseAttachmentRepository
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class CourseAttachmentReactiveRepository(
    private val databaseClient: DatabaseClient,
    private val mapper: CourseAttachmentMapper
) : CourseAttachmentRepository {
    private val clazz = CourseAttachmentEntity::class.java

    override fun create(courseAttachment: CourseAttachment): Mono<CourseAttachment> {
        return databaseClient
            .insert()
            .into(clazz)
            .using(mapper.map(courseAttachment))
            .then()
            .thenReturn(courseAttachment)
    }

    override fun update(courseAttachment: CourseAttachment): Mono<CourseAttachment> {
        return databaseClient
            .update()
            .table(clazz)
            .using(mapper.map(courseAttachment))
            .matching(Criteria.where("id").`is`(courseAttachment.id))
            .fetch()
            .rowsUpdated()
            .thenReturn(courseAttachment)
    }

    override fun findById(id: UUID): Mono<CourseAttachment> {
        return databaseClient
            .select()
            .from(clazz)
            .matching(Criteria.where("id").`is`(id))
            .fetch()
            .one()
            .map { mapper.map(it) }
    }

    override fun findByStudentId(studentId: UUID): Flux<CourseAttachment> {
        return databaseClient
            .select()
            .from(clazz)
            .matching(Criteria.where("student_id").`is`(studentId))
            .fetch()
            .all()
            .map { mapper.map(it) }
    }
}