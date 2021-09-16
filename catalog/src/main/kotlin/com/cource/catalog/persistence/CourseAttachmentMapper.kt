package com.cource.catalog.persistence

import com.cource.catalog.domain.CourseAttachment
import org.springframework.stereotype.Component

@Component
class CourseAttachmentMapper {
    fun map(entity: CourseAttachmentEntity): CourseAttachment = with(entity) {
        CourseAttachment(id, courseId, studentId, startAt)
    }

    fun map(domain: CourseAttachment): CourseAttachmentEntity = with(domain) {
        CourseAttachmentEntity(id, courseId, studentId, startAt)
    }
}