package com.jsv.restrace.incidents

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jsv.restrace.config.RestraceProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder

@Component
@ConditionalOnProperty(prefix = "restrace", name = ["incident-collection-enabled"], havingValue = "true")
class AzdopsIncidentCollector(
    private val properties: RestraceProperties,
    val restClient: RestClient,
) : IncidentCollector {
    val log = KotlinLogging.logger {}

    val azDopsIncidentsUri: String =
        UriComponentsBuilder
            .fromUriString(properties.azDopsEndpoint ?: "")
            .path("/_apis/wit/wiql/${properties.azDopsQueryId}")
            .queryParam("api-version", "7.1")
            .toUriString()

    override fun collectIncident(): Result<String, Throwable> {
        val result =
            restClient
                .get()
                .uri(azDopsIncidentsUri)
                .header("Authorization", "Bearer ${properties.azDopsToken}")
                .retrieve()
                .toEntity(String::class.java)
        log.info { "Incidents collected from $azDopsIncidentsUri:\n$result" }
        return Ok("Incidents collected")
    }
}
