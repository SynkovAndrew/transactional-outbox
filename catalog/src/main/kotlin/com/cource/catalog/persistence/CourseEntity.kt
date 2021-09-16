package com.cource.catalog.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("course")
data class CourseEntity(
    @Id
    val id: UUID,
    val name: String
)
