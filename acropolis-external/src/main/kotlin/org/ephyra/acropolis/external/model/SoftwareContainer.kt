package org.ephyra.acropolis.external.model

/**
 * Model for grouping applications and systems together
 */
data class SoftwareContainer (
        var applications: List<ApplicationSoftware>,
        var systems: List<SystemSoftware>
)
