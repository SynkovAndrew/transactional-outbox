package com.cource.student.domain

import java.time.LocalDateTime
import java.util.*

interface StudentDomainEvent : DomainEvent {
    val studentId: UUID
}

sealed class AbstractStudentDomainEvent(
    override val id: UUID = UUID.randomUUID(),
    override val timestamp: LocalDateTime = LocalDateTime.now(),
) : StudentDomainEvent

data class StudentEnrolledDomainEvent(
    override val studentId: UUID
) : AbstractStudentDomainEvent()