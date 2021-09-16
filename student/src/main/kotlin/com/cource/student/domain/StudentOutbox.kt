package com.cource.student.domain

import java.util.*

data class StudentOutbox(
    val id: UUID,
    val payload: String
)