package com.cource.student.rest

import java.util.*

data class StudentJsonBean(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val status: String
)
