package com.jsv.restrace.incidents

import io.github.oshai.kotlinlogging.KotlinLogging
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.instrumentation.annotations.WithSpan
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@ConditionalOnProperty(prefix = "restrace", name = ["incident-collection-enabled"], havingValue = "true")
class IncidentsController(
    val incidentCollector: IncidentCollector,
) {
    val log = KotlinLogging.logger {}

    @PostMapping("/collect/incidents")
    @WithSpan("incident")
    fun collectIncident(): ResponseEntity<String> {
        val span = Span.current()
        val result = incidentCollector.collectIncident()
        log.info { "Collect incidents called" }
        span.setStatus(
            when {
                result.isOk -> StatusCode.OK
                else -> StatusCode.ERROR
            },
        )
        return ResponseEntity
            .status(
                when {
                    result.isOk -> 200
                    else -> 500
                },
            ).body(
                when {
                    result.isOk -> result.value
                    else -> "Incident collection failed"
                },
            )
    }
}
