package com.cource.catalog.usecase

import com.cource.catalog.domain.CourseAttachment
import com.cource.catalog.domain.CourseAttachmentRepository
import com.cource.catalog.domain.CourseRepository
import com.cource.catalog.infrastructure.ExternalStudentEventListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Component
class AssignCoursesUseCase(
    private val courseRepository: CourseRepository,
    private val courseAttachmentRepository: CourseAttachmentRepository
) {

    @Transactional
    fun handle(command: AssignCoursesCommand): Mono<AssignCoursesResponse> {
        logger.info("AssignCoursesCommand: $command is being handled!")
        return courseRepository.findAll()
            .map {
                CourseAttachment(
                    id = UUID.randomUUID(),
                    courseId = it.id,
                    studentId = command.studentId,
                    startAt = LocalDateTime.now().minusDays(14).withNano(0)
                )
            }
            .flatMap { courseAttachmentRepository.create(it) }
            .map { it.courseId }
            .collectList()
            .map {
                AssignCoursesResponse(
                    studentId = command.studentId,
                    courseIds = it.toSet()
                )
            }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AssignCoursesUseCase::class.java)
    }
}

data class AssignCoursesCommand(
    val studentId: UUID
)

data class AssignCoursesResponse(
    val studentId: UUID,
    val courseIds: Set<UUID>
)
