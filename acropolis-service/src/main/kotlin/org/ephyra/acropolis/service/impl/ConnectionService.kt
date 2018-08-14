package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.ConnectionPersistence
import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.ephyra.acropolis.service.api.IConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectionService : IConnectionService {
    @Autowired
    private lateinit var persistence: ConnectionPersistence

    override fun <F: Any, T: Any> create(fromId: Long, fromType: F, toId: Long, toType: T) {
        val connection = ConnectionEntity(fromId, fromType.javaClass.name, toId, toType.javaClass.name)
        persistence.create(connection)
    }
}