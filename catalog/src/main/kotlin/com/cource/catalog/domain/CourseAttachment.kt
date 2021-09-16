package com.cource.catalog.domain

import java.time.LocalDateTime
import java.util.*

data class CourseAttachment(
    val id: UUID,
    val courseId: UUID,
    val studentId: UUID,
    val startAt: LocalDateTime
)
