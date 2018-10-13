package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Repository for persisting ApplicationSoftwareEntity
 */
internal interface ApplicationSoftwareRepository : CrudRepository<ApplicationSoftwareEntity, Long> {
    /**
     * Finds an application by name and project id.
     *
     * @param name The name of the entity to find
     * @param projectId the ID of the project this name is scoped to
     * @return An instance of ApplicationSoftwareEntity if one is found
     */
    fun findByNameAndProjectId(name: String, projectId: Long): Optional<ApplicationSoftwareEntity>
}
