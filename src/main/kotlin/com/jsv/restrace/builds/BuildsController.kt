package com.jsv.restrace.builds

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy::class)
data class Pipeline(
    val service: String,
    val buildId: String,
    val status: String,
    val branch: String,
    val startTime: Long,
    val endTime: Long,
    val stages: List<Stage>,
)

data class Stage(
    val name: String,
    val status: String,
    val startTime: Long,
    val endTime: Long,
)

@RestController
@RequestMapping("/api/v1")
class BuildsController {
    val log = KotlinLogging.logger {}

    @PostMapping("/collect/build")
    fun collect(
        @RequestBody pipeline: Pipeline,
    ): String {
        log.info { "Build collected $pipeline" }
        return "Build collected"
    }
}
