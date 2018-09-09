package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.ephyra.acropolis.persistence.impl.ConnectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConnectionPersistence {
    @Autowired
    private lateinit var repository: ConnectionRepository

    /**
     * Create a new record for the entity in the database
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: ConnectionEntity) {
        repository.save(entity)
    }

    /**
     * @param fromId the ID of the entity to get connections from
     * @param fromEndpointType the type of connections to query for
     * @return all of the connections of the specified type from the entity corresponding to the specified ID
     */
    fun getConnectionsFrom(fromId: Long, fromEndpointType: Int): List<ConnectionEntity> {
        return repository.getByFromIdAndFromEndpointType(fromId, fromEndpointType)
    }
}
