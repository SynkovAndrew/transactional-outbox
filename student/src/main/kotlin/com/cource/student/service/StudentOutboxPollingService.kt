package com.cource.student.service

import com.cource.student.domain.StudentOutboxRepository
import com.cource.student.infrastructure.ExternalStudentEventFactory
import com.cource.student.infrastructure.ExternalStudentEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class StudentOutboxPollingService(
    private val studentOutboxRepository: StudentOutboxRepository,
    private val externalStudentEventFactory: ExternalStudentEventFactory,
    private val externalStudentEventPublisher: ExternalStudentEventPublisher
) {

    @Transactional
    fun poll(): Mono<Void> {
        return studentOutboxRepository.findNext()
            .doOnNext { logger.info("Student outbox $it is being processed!") }
            .flatMap { studentOutbox ->
                externalStudentEventFactory.build(studentOutbox)
                    .toMono()
                    .doOnNext { externalStudentEvent -> externalStudentEventPublisher.publish(externalStudentEvent) }
                    .flatMap { studentOutboxRepository.delete(studentOutbox) }
            }
            .then()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(StudentOutboxPollingService::class.java)
    }
}