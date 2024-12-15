package com.jsv.restrace

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false)
class TestRestraceApplication

fun main(args: Array<String>) {
    fromApplication<RestraceApplication>().with(TestRestraceApplication::class).run(*args)
}
