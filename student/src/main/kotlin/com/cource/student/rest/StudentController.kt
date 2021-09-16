package com.cource.student.rest

import com.cource.student.usecase.EnrollStudentCommand
import com.cource.student.usecase.EnrollStudentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@RestController
@RequestMapping("/student")
class StudentController(
    private val enrollStudentUseCase: EnrollStudentUseCase,
    private val studentJsonBeanMapper: StudentJsonBeanMapper
) {

    @PostMapping("/{studentId}/enroll")
    fun enroll(
        @PathVariable studentId: UUID,
    ): Mono<ResponseEntity<StudentJsonBean>> {
        return studentId.toMono()
            .map { EnrollStudentCommand(it) }
            .flatMap { enrollStudentUseCase.handle(it) }
            .map { studentJsonBeanMapper.map(it) }
            .map { ResponseEntity.ok(it) }
    }
}