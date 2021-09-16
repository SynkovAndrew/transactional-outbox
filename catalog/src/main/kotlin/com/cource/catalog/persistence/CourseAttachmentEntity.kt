package com.cource.catalog.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("course_attachment")
data class CourseAttachmentEntity(
    @Id
    val id: UUID,
    val courseId: UUID,
    val studentId: UUID,
    val startAt: LocalDateTime
)
