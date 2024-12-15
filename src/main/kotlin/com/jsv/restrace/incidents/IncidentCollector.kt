package com.jsv.restrace.incidents

interface IncidentCollector {
    fun collectIncident(): Result<String>
}
