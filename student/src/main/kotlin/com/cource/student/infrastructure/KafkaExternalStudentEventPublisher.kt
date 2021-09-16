package com.cource.student.infrastructure

import com.course.infrastructure.ExternalStudentEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaExternalStudentEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, ExternalStudentEvent>
): ExternalStudentEventPublisher  {

    override fun publish(event: ExternalStudentEvent) {
        logger.info("ExternalStudentEvent $event is being published to topic")
        kafkaTemplate.send("external-student-event-topic", event)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaExternalStudentEventPublisher::class.java)
    }
}