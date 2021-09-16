package com.cource.catalog.persistence

import com.cource.catalog.domain.Course
import org.springframework.stereotype.Component

@Component
class CourseMapper {
    fun map(entity: CourseEntity): Course = with(entity) {
        Course(id, name)
    }

    fun map(domain: Course): CourseEntity = with(domain) {
        CourseEntity(id, name)
    }
}