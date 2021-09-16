package com.cource.student.persistence

import com.cource.student.domain.StudentOutbox
import org.springframework.stereotype.Component

@Component
class StudentOutboxMapper {
    fun map(entity: StudentOutboxEntity): StudentOutbox = with(entity) {
        StudentOutbox(id, payload)
    }

    fun map(domain: StudentOutbox): StudentOutboxEntity = with(domain) {
        StudentOutboxEntity(id, payload)
    }
}