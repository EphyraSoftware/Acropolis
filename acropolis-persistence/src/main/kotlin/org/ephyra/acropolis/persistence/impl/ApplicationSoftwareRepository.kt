package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface ApplicationSoftwareRepository : CrudRepository<ApplicationSoftwareEntity, Long> {
    fun findByNameAndProjectId(name: String, projectId: Int): Optional<ApplicationSoftwareEntity>
}
