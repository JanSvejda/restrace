package com.jsv.restrace.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationProperties(prefix = "restrace")
@ConfigurationPropertiesScan
data class RestraceProperties(
    var incidentCollectionEnabled: Boolean = false,
    var azDopsEndpoint: String? = null, // e.g., https://{instance}/fabrikam/Fabrikam-Fiber-Git
    var azDopsToken: String? = null, // a PAT token
    var azDopsQueryId: String? = null, // a query ID returning count of bugs
)
