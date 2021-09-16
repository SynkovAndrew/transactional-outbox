package com.cource.student.domain

import reactor.core.publisher.Mono
import java.util.*

interface StudentRepository {
    fun create(student: Student): Mono<Student>

    fun update(student: Student): Mono<Student>

    fun findById(id: UUID): Mono<Student>
}