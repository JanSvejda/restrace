package com.jsv.restrace.incidents

import com.github.michaelbull.result.Ok
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.jsv.restrace.config.RestraceProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestClient
import org.wiremock.spring.EnableWireMock

@SpringBootTest
@EnableWireMock
class AzdopsIncidentCollectorTest {
    @Value("\${wiremock.server.baseUrl}")
    lateinit var wireMockUrl: String

    @Test
    fun `collect incidents from Azure DevOps`() {
        stubFor(
            get(urlEqualTo("/_apis/wit/wiql/testQueryId?api-version=7.1"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("Incidents collected"),
                ),
        )

        val collector = AzdopsIncidentCollector(RestraceProperties(true, wireMockUrl, null, "testQueryId"), RestClient.create())
        Assertions.assertEquals(Ok("Incidents collected"), collector.collectIncident())
    }
}
