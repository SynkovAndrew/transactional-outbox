package com.cource.student.domain

import com.course.infrastructure.ExternalStudentEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudentOutboxFactory(
    private val objectMapper: ObjectMapper
) {
    fun build(student: Student): StudentOutbox {
        return StudentOutbox(
            id = UUID.randomUUID(),
            payload = objectMapper.writeValueAsString(
                ExternalStudentEvent(
                    studentId = student.id,
                    firstName = student.firstName,
                    lastName = student.lastName,
                    middleName = student.middleName,
                    email = student.email,
                    status = student.status.name
                )
            )
        )
    }
}