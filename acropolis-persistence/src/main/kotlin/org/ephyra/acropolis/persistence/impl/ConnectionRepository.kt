package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.springframework.data.repository.CrudRepository

/**
 * Spring Repository for persisting ConnectionEntity
 */
internal interface ConnectionRepository : CrudRepository<ConnectionEntity, Long> {
    /**
     * Finds a the 'from' end of a connection by id and type.
     *
     * @param fromId The ID of the entity to find connections from
     * @param fromEndpointType the type of connection to find
     * @return An List<> of all the instances of ConnectionEntity that were found
     */
    fun getByFromIdAndFromEndpointType(fromId: Long, fromEndpointType: Int): List<ConnectionEntity>
}
