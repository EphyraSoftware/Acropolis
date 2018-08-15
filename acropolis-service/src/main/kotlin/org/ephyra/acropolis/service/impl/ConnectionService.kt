package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.persistence.ConnectionPersistence
import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.ephyra.acropolis.service.api.IConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectionService : IConnectionService {
    @Autowired
    private lateinit var persistence: ConnectionPersistence

    override fun create(fromConnectable: IConnectable, toConnectable: IConnectable) {
        val connection = ConnectionEntity(
                fromConnectable.getConnectionId(),
                fromConnectable.getConnectionType(),
                toConnectable.getConnectionId(),
                toConnectable.getConnectionType()
        )

        persistence.create(connection)
    }
}
