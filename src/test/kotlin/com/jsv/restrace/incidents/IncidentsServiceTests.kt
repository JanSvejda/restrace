package com.jsv.restrace.incidents

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["restrace.incident-collection-enabled=true"])
@AutoConfigureMockMvc
class IncidentsServiceTests {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `triggering incident collection should succeed`() {
        mvc
            .perform(post("/api/v1/collect/incidents"))
            .andExpect(status().isOk)
            .andExpect(content().string("Incidents collected"))
    }
}
