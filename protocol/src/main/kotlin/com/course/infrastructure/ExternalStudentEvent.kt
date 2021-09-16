package com.course.infrastructure

import java.util.*

data class ExternalStudentEvent(
    val studentId: UUID,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val status: String
)