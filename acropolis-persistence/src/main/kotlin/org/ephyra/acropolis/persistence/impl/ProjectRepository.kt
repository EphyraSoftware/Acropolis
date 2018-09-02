package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface ProjectRepository : CrudRepository<ProjectEntity, Long> {
    fun findByName(name: String): Optional<ProjectEntity>
}
