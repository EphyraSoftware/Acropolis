package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface ComputeInstanceRepository : CrudRepository<ComputeInstanceEntity, Long> {
    fun findByNameAndProjectId(name: String, projectId: Long): Optional<ComputeInstanceEntity>
}
