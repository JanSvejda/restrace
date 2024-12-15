package com.jsv.restrace.incidents

import io.github.oshai.kotlinlogging.KotlinLogging
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.instrumentation.annotations.WithSpan
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@ConditionalOnProperty(prefix = "restrace", name = ["incident-collection-enabled"], havingValue = "true")
class IncidentsService(
    val incidentCollector: IncidentCollector,
) {
    val log = KotlinLogging.logger {}

    @PostMapping("/collect/incidents")
    @WithSpan("incident")
    fun collectIncident(): String {
        val span = Span.current()
        val result = incidentCollector.collectIncident()
        result.map { span.setAttribute("count", it) }
        span.setStatus(StatusCode.OK)
        log.info { "Incidents collected" }
        // get bugs via /_apis/wit/wiql/{id}?api-version=7.1
        return "Incidents collected"
    }
}
