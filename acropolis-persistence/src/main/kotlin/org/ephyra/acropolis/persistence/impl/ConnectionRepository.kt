package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.springframework.data.repository.CrudRepository

internal interface ConnectionRepository : CrudRepository<ConnectionEntity, Long> {
    fun getByFromIdAndFromType(fromId: Long, fromEndpointType: Int): List<ConnectionEntity>
}
