package org.ephyra.acropolis.external.model

/**
 * Model for system software
 */
data class SystemSoftware (
        var name: String,
        var description: String,
        var talks_to: List<String>?
)
