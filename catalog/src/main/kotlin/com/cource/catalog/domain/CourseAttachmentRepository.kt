package com.cource.catalog.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface CourseAttachmentRepository {
    fun create(courseAttachment: CourseAttachment): Mono<CourseAttachment>

    fun update(courseAttachment: CourseAttachment): Mono<CourseAttachment>

    fun findById(id: UUID): Mono<CourseAttachment>

    fun findByStudentId(studentId: UUID): Flux<CourseAttachment>
}