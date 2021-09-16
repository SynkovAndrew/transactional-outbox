package com.cource.student.persistence

import com.cource.student.domain.Student
import com.cource.student.domain.StudentOutbox
import com.cource.student.domain.StudentOutboxRepository
import com.cource.student.domain.StudentStatus
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.kotlin.test.test
import java.util.*


@SpringBootTest
class StudentOutboxRepositoryTest {
    @Autowired
    lateinit var studentOutboxRepository: StudentOutboxRepository
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun create() {
        val studentOutboxId = UUID.randomUUID()
        val student = Student(
            id = UUID.randomUUID(),
            firstName = "Ivan",
            lastName = "Sokolov",
            middleName = "Vasilivich",
            email = "ive@gmail.com",
            status = StudentStatus.ENROLLED
        )
        val studentOutbox = StudentOutbox(
            id = studentOutboxId,
            payload = objectMapper.writeValueAsString(student)
        )
        studentOutboxRepository.create(studentOutbox)
            .test()
            .expectNext(studentOutbox)
            .verifyComplete()

        studentOutboxRepository.findById(studentOutboxId)
            .test()
            .assertNext {
                assertThat(it.id).isEqualTo(studentOutbox.id)
                assertThat(objectMapper.readValue(it.payload, Student::class.java)).isEqualTo(student)
            }
            .verifyComplete()
    }
}