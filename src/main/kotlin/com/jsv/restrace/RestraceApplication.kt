package com.jsv.restrace

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.opentelemetry.instrumentation.spring.autoconfigure.EnableOpenTelemetry
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestClient

@SpringBootApplication
@EnableOpenTelemetry
@ConfigurationPropertiesScan("com.jsv.restrace.config")
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

    @Bean
    fun restClient(): RestClient =
        RestClient
            .builder()
            .build()
}

fun main(args: Array<String>) {
    runApplication<RestraceApplication>(*args)
}
