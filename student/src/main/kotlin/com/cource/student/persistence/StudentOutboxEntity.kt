package com.cource.student.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("student_outbox")
class StudentOutboxEntity(
    @Id
    val id: UUID,
    val payload: String
)