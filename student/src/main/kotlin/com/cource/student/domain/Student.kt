package com.cource.student.domain

import com.cource.student.domain.exception.StudentIllegalStateException
import java.util.*

data class Student(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val status: StudentStatus,
    private var events: MutableList<StudentDomainEvent> = mutableListOf()
) {
    fun enroll(): Student {
        if (status != StudentStatus.NEW) {
            throw StudentIllegalStateException("Student in not NEW status!")
        }

        return copy(status = StudentStatus.ENROLLED)
            .registerEvent { StudentEnrolledDomainEvent(studentId = id) }
    }

    fun getEvents(): List<StudentDomainEvent> = ArrayList(events)

    private fun registerEvent(block: (Student) -> StudentDomainEvent): Student {
        val result = ArrayList(this.events)
        result.add(block(this))
        events = result
        return this
    }
}

enum class StudentStatus {
    NEW,
    ENROLLED
}