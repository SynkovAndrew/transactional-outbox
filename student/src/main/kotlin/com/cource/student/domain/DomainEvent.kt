package com.cource.student.domain

import java.time.LocalDateTime
import java.util.*

interface DomainEvent {
    val id: UUID
    val timestamp: LocalDateTime
}