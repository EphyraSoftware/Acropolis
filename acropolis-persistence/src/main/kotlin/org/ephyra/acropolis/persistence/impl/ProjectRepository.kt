package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface ProjectRepository : CrudRepository<ProjectEntity, Long> {
    /**
     * @param name The name of the entity to find
     * @return An instance of ProjectEntity if one is found
     * */
    fun findByName(name: String): Optional<ProjectEntity>
}
