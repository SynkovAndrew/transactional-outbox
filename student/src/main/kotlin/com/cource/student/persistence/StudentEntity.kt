package com.cource.student.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("student")
class StudentEntity(
    @Id
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val status: String
)