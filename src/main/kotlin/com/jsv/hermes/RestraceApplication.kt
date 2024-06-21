package com.jsv.restrace

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.opentelemetry.instrumentation.spring.autoconfigure.EnableOpenTelemetry
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableOpenTelemetry
class RestraceApplication {
    val log = logger {}

    // log the application start and end on spring initialization
    @PostConstruct
    fun init() {
        log.info { "restrace application started" }
    }

    @PreDestroy
    fun destroy() {
        log.info { "restrace application stopped" }
    }

}

fun main(args: Array<String>) {
    runApplication<RestraceApplication>(*args)
}

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy::class)
data class Pipeline(
    val service: String,
    val buildId: String,
    val status: String,
    val branch: String,
    val startTime: Long,
    val endTime: Long,
    val stages: List<Stage>
)

data class Stage(val name: String, val status: String, val startTime: Long, val endTime: Long)

@RestController
@RequestMapping("/api/v1")
class CollectionService {

    val log = logger {}

    @PostMapping("/collect/build")
    @WithSpan("dora")
    fun collect(@RequestBody pipeline: Pipeline): String {
        val span = Span.current()
        span.setStatus(StatusCode.OK)
        span.setAttribute("service", pipeline.service)
        span.setAttribute("start_time", pipeline.startTime)
        span.setAttribute("end_time", pipeline.endTime)
        span.setAttribute("status", pipeline.status)
        span.setAttribute("branch", pipeline.branch)
        for (stage in pipeline.stages) {
            span.setAttribute("${stage.name}_start_time", stage.startTime)
            span.setAttribute("${stage.name}_end_time", stage.endTime)
            span.setAttribute("${stage.name}_status", stage.status)
        }
        log.info { "Build collected $pipeline" }
        return "Build collected"
    }
}