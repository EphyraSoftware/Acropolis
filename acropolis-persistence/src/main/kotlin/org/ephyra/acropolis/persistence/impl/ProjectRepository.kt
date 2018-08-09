package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.springframework.data.repository.CrudRepository

internal interface ProjectRepository : CrudRepository<ProjectEntity, Long> {
    fun findByName(name: String): ProjectEntity
}
