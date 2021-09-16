package com.cource.student.rest

import com.cource.student.domain.Student
import com.cource.student.domain.StudentRepository
import com.cource.student.domain.StudentStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.util.*


@SpringBootTest
@AutoConfigureWebTestClient
class StudentControllerTest {
    @Autowired
    lateinit var studentRepository: StudentRepository
    @Autowired
    lateinit var webClient: WebTestClient

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

        val expectedStudent = Student(
            id = student.id,
            firstName = "Ivan",
            lastName = "Sokolov",
            middleName = "Vasilivich",
            email = "ive@gmail.com",
            status = StudentStatus.ENROLLED
        )

        studentRepository.create(student)
            .test()
            .expectNext(student)
            .verifyComplete()

        webClient.post()
            .uri("/student/${student.id}/enroll")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(student.id.toString())
            .jsonPath("$.firstName").isEqualTo("Ivan")
            .jsonPath("$.lastName").isEqualTo("Sokolov")
            .jsonPath("$.middleName").isEqualTo("Vasilivich")
            .jsonPath("$.email").isEqualTo("ive@gmail.com")
            .jsonPath("$.status").isEqualTo(StudentStatus.ENROLLED.name)

        studentRepository.findById(student.id)
            .test()
            .expectNext(expectedStudent)
            .verifyComplete()
    }

}