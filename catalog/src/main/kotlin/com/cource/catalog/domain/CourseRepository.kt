package com.cource.catalog.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface CourseRepository {
    fun create(course: Course): Mono<Course>

    fun update(course: Course): Mono<Course>

    fun findById(id: UUID): Mono<Course>

    fun findAll(): Flux<Course>
}