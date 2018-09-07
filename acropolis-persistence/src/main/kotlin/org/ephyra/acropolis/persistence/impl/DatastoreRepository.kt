package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface DatastoreRepository : CrudRepository<DatastoreEntity, Long> {
    fun findByNameAndProjectId(name: String, projectId: Long): Optional<DatastoreEntity>
}
