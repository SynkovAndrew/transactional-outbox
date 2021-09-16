package com.cource.student.persistence

import com.cource.student.domain.Student
import com.cource.student.domain.StudentStatus
import org.springframework.stereotype.Component

@Component
class StudentMapper {
    fun map(entity: StudentEntity): Student = with(entity) {
        Student(id, firstName, lastName, middleName, email, StudentStatus.valueOf(status))
    }

    fun map(domain: Student): StudentEntity = with(domain) {
        StudentEntity(id, firstName, lastName, middleName, email, status.name)
    }
}