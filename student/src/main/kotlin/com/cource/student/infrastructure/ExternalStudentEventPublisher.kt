package com.cource.student.infrastructure

import com.course.infrastructure.ExternalStudentEvent

interface ExternalStudentEventPublisher {

    fun publish(event: ExternalStudentEvent)
}