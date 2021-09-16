package com.cource.catalog.persistence

import com.cource.catalog.domain.Course
import com.cource.catalog.domain.CourseRepository
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class CourseReactiveRepository(
    private val databaseClient: DatabaseClient,
    private val mapper: CourseMapper
) : CourseRepository {
    private val clazz = CourseEntity::class.java

    override fun create(course: Course): Mono<Course> {
        return databaseClient
            .insert()
            .into(clazz)
            .using(mapper.map(course))
            .then()
            .thenReturn(course)
    }

    override fun update(course: Course): Mono<Course> {
        return databaseClient
            .update()
            .table(clazz)
            .using(mapper.map(course))
            .matching(Criteria.where("id").`is`(course.id))
            .fetch()
            .rowsUpdated()
            .thenReturn(course)
    }

    override fun findById(id: UUID): Mono<Course> {
        return databaseClient
            .select()
            .from(clazz)
            .matching(Criteria.where("id").`is`(id))
            .fetch()
            .one()
            .map { mapper.map(it) }
    }

    override fun findAll(): Flux<Course> {
        return databaseClient
            .select()
            .from(clazz)
            .fetch()
            .all()
            .map { mapper.map(it) }
    }

}