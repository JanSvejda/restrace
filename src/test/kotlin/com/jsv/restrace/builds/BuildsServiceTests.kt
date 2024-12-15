package com.jsv.restrace.builds

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BuildsServiceTests {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `build collection should succeed`() {
        mvc
            .perform(
                post("/api/v1/collect/build").contentType(MediaType.APPLICATION_JSON).content(
                    """
                    {"service": "1234", "build-id": "12345", "branch": "main", "status": "success", "start-time": 1234, "end-time": 5432, "stages": [{"name": "labs", "start-time": 1234, "end-time": 4321, "status": "success"}]}
                    """.trimIndent(),
                ),
            ).andExpect(status().isOk)
            .andExpect(content().string("Build collected"))
    }
}
