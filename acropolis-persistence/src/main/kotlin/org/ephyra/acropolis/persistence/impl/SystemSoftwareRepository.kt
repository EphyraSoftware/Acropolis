package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface SystemSoftwareRepository : CrudRepository<SystemSoftwareEntity, Long> {
    fun findByNameAndProjectId(name: String, projectId: Int): Optional<SystemSoftwareEntity>
}
