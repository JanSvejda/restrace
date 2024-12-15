package com.jsv.restrace.incidents

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "restrace", name = ["incident-collection-enabled"], havingValue = "true")
class AzdopsIncidentCollector : IncidentCollector {
    override fun collectIncident(): Result<String> = Result.success("Incident collected")
}
