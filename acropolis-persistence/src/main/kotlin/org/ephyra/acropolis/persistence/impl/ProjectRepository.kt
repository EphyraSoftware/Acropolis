package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Repository for persisting ProjectEntity
 */
internal interface ProjectRepository : CrudRepository<ProjectEntity, Long> {
    /**
     * Finds a project by name.
     *
     * @param name The name of the entity to find
     * @return An instance of ProjectEntity if one is found
     */
    fun findByName(name: String): Optional<ProjectEntity>
}
