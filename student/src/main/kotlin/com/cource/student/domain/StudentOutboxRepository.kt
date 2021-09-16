package com.cource.student.domain

import reactor.core.publisher.Mono
import java.util.*

interface StudentOutboxRepository {
    fun create(studentOutbox: StudentOutbox): Mono<StudentOutbox>

    fun delete(studentOutbox: StudentOutbox): Mono<StudentOutbox>

    fun findById(id: UUID): Mono<StudentOutbox>

    fun findNext(): Mono<StudentOutbox>
}