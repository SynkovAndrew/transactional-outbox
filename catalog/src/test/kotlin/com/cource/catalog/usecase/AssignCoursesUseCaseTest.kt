package com.cource.catalog.usecase

import com.cource.catalog.domain.Course
import com.cource.catalog.domain.CourseAttachmentRepository
import com.cource.catalog.domain.CourseRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.kotlin.test.test
import java.util.*

@SpringBootTest
class AssignCoursesUseCaseTest {
    @Autowired
    lateinit var courseRepository: CourseRepository
    @Autowired
    lateinit var courseAttachmentRepository: CourseAttachmentRepository
    @Autowired
    lateinit var assignCoursesUseCase: AssignCoursesUseCase

    private val mathematicsCourseId = UUID.randomUUID()
    private val physicsCourseId = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        courseRepository.create(Course(
            id = mathematicsCourseId,
            name = "Mathematics"
        )).block()
        courseRepository.create(Course(
            id = physicsCourseId,
            name = "Physics"
        )).block()
    }

    @Test
    fun handle() {
        val studentId = UUID.randomUUID()
        assignCoursesUseCase.handle(AssignCoursesCommand(studentId))
            .test()
            .expectNext(AssignCoursesResponse(studentId, setOf(mathematicsCourseId, physicsCourseId)))
            .verifyComplete()
        courseAttachmentRepository.findByStudentId(studentId)
            .test()
            .expectNextCount(2)
            .verifyComplete()
    }
}