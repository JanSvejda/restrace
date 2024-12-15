package com.jsv.restrace.incidents

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import jakarta.servlet.UnavailableException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["restrace.incident-collection-enabled=true"])
@AutoConfigureMockMvc
class IncidentsControllerTests {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    lateinit var azdopsService: AzdopsIncidentCollector

    @Test
    fun `triggering incident collection should succeed`() {
        `when`(azdopsService.collectIncident()).thenReturn(Ok("Incidents collected"))
        mvc
            .perform(post("/api/v1/collect/incidents"))
            .andExpect(status().isOk)
            .andExpect(content().string("Incidents collected"))
    }

    @Test
    fun `triggering incident collection should fail`() {
        `when`(azdopsService.collectIncident()).thenReturn(Err(UnavailableException("Service unavailable")))
        mvc
            .perform(post("/api/v1/collect/incidents"))
            .andExpect(status().is5xxServerError)
    }
}
