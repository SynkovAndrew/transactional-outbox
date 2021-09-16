package com.cource.catalog.infrastructure

import com.cource.catalog.usecase.AssignCoursesCommand
import com.cource.catalog.usecase.AssignCoursesUseCase
import com.course.infrastructure.ExternalStudentEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ExternalStudentEventListener(
    private val assignCoursesUseCase: AssignCoursesUseCase
) {

    @KafkaListener(topics = ["external-student-event-topic"], groupId = "external-student-event-group-id")
    fun listen(externalStudentEvent: ExternalStudentEvent) {
        logger.info("Event: $externalStudentEvent is being handled!")

        if (externalStudentEvent.status == "ENROLLED") {
            assignCoursesUseCase.handle(AssignCoursesCommand(externalStudentEvent.studentId))
                .block()
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ExternalStudentEventListener::class.java)
    }
}