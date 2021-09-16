package com.cource.student.infrastructure

import com.cource.student.domain.StudentOutbox
import com.course.infrastructure.ExternalStudentEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class ExternalStudentEventFactory(
    private val objectMapper: ObjectMapper
) {
    fun build(studentOutbox: StudentOutbox): ExternalStudentEvent {
        return objectMapper.readValue(studentOutbox.payload, ExternalStudentEvent::class.java)
    }
}