package org.ephyra.acropolis.persistence.api

import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.ephyra.acropolis.persistence.impl.ConnectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConnectionPersistence {
    @Autowired
    private lateinit var repository: ConnectionRepository

    fun create(connection: ConnectionEntity) {
        repository.save(connection)
    }
}