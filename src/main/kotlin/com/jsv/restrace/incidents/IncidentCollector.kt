package com.jsv.restrace.incidents

import com.github.michaelbull.result.Result

interface IncidentCollector {
    fun collectIncident(): Result<String, Throwable>
}
