package org.ephyra.acropolis.external.model

/**
 * Model for applications
 */
data class ApplicationSoftware (
        var name: String,
        var description: String,
        var talks_to: List<String>?
)
