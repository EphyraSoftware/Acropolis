package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Repository for persisting SystemSoftwareEntity
 */
internal interface SystemSoftwareRepository : CrudRepository<SystemSoftwareEntity, Long> {
    /**
     * Finds a system by name and project id.
     *
     * @param name The name of the entity to find
     * @param projectId the ID of the project this name is scoped to
     * @return An instance of SystemSoftwareEntity if one is found
     */
    fun findByNameAndProjectId(name: String, projectId: Long): Optional<SystemSoftwareEntity>

    fun findByProjectId(projectId: Long): List<SystemSoftwareEntity>
}
