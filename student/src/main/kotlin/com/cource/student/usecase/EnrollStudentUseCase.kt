package com.cource.student.usecase

import com.cource.student.domain.Student
import com.cource.student.domain.StudentOutboxFactory
import com.cource.student.domain.StudentOutboxRepository
import com.cource.student.domain.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@Component
class EnrollStudentUseCase(
    private val studentRepository: StudentRepository,
    private val studentOutboxRepository: StudentOutboxRepository,
    private val studentOutboxFactory: StudentOutboxFactory,
) {
    @Transactional
    fun handle(command: EnrollStudentCommand): Mono<Student> {
        logger.info("EnrollStudentCommand: $command is being handled!")
        return command.studentId.toMono()
            .flatMap { studentId -> studentRepository.findById(studentId) }
            .map { student -> student.enroll() }
            .flatMap { student ->
                studentRepository.update(student)
                    .map { studentOutboxFactory.build(it) }
                    .flatMap { studentOutboxRepository.create(it) }
                    .thenReturn(student)
            }
           // .doOnSuccess { student -> domainEventPublisher.publish(student.getEvents()) }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(EnrollStudentUseCase::class.java)
    }
}

data class EnrollStudentCommand(
    val studentId: UUID
)