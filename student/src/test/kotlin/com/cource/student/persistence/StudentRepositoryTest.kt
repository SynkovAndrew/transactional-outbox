package com.cource.student.persistence

import com.cource.student.domain.Student
import com.cource.student.domain.StudentRepository
import com.cource.student.domain.StudentStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.util.*


@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    lateinit var studentRepository: StudentRepository

    @Test
    fun create() {
        val student = Student(
            id = UUID.randomUUID(),
            firstName = "Ivan",
            lastName = "Sokolov",
            middleName = "Vasilivich",
            email = "ive@gmail.com",
            status = StudentStatus.NEW
        )

        studentRepository.create(student)
            .test()
            .expectNext(student)
            .verifyComplete()
        studentRepository.findById(student.id)
            .test()
            .expectNext(student)
            .verifyComplete()
    }

    @Test
    fun update() {
        val studentId = UUID.randomUUID()
        val student = Student(
            id = studentId,
            firstName = "Ivan",
            lastName = "Sokolov",
            middleName = "Vasilivich",
            email = "ive@gmail.com",
            status = StudentStatus.NEW
        )

        val updatedStudent = Student(
            id = studentId,
            firstName = "Haniv",
            lastName = "Tracel",
            middleName = "Maag",
            email = "maak@gmail.com",
            status = StudentStatus.ENROLLED
        )

        studentRepository.create(student)
            .test()
            .expectNext(student)
            .verifyComplete()
        studentRepository.update(updatedStudent)
            .test()
            .expectNext(updatedStudent)
            .verifyComplete()
        studentRepository.findById(studentId)
            .test()
            .expectNext(updatedStudent)
            .verifyComplete()
    }
}