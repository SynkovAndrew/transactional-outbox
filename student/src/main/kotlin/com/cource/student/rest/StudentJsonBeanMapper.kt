package com.cource.student.rest

import com.cource.student.domain.Student
import com.cource.student.domain.StudentStatus
import org.springframework.stereotype.Component

@Component
class StudentJsonBeanMapper {
    fun map(entity: StudentJsonBean): Student = with(entity) {
        Student(id, firstName, lastName, middleName, email, StudentStatus.valueOf(status))
    }

    fun map(domain: Student): StudentJsonBean = with(domain) {
        StudentJsonBean(id, firstName, lastName, middleName, email, status.name)
    }
}
