package com.cource.student.service

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import javax.annotation.PostConstruct

@Component
class StudentOutboxPoller(
    private val studentOutboxPollingService: StudentOutboxPollingService
) {

    @PostConstruct
    fun init() {
        Flux.interval(Duration.ofSeconds(10))
            .flatMap { studentOutboxPollingService.poll() }
            .subscribe()
    }
}