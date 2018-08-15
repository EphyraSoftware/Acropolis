package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.persistence.api.persistence.ConnectionPersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectionService : IConnectionService {
    @Autowired
    private lateinit var persistence: ConnectionPersistence

    @Autowired
    private lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    @Autowired
    private lateinit var applicationSoftwarePersistence: ApplicationSoftwarePersistence

    override fun create(fromConnectable: IConnectable, toConnectable: IConnectable) {
        val connection = ConnectionEntity(
                fromConnectable.getConnectionId(),
                fromConnectable.getConnectionType(),
                toConnectable.getConnectionId(),
                toConnectable.getConnectionType()
        )

        persistence.create(connection)
    }

    override fun getConnectionsFrom(fromConnectable: IConnectable): List<IConnectable> {
        val connections = persistence.getConnectionsFrom(fromConnectable.getConnectionId(), fromConnectable.getConnectionType())

        val res = connections.map { connectionEntity ->
            when (ConnectionType.fromInt(connectionEntity.toType)) {
                ConnectionType.SYSTEM_SOFTWARE -> systemSoftwarePersistence.find(connectionEntity.toId)
                ConnectionType.APPLICATION_SOFTWARE ->applicationSoftwarePersistence.find(connectionEntity.toId)
                else -> throw IllegalStateException("Connection entity with connection to unknown type [${connectionEntity.toType}")
            }
        }

        return res.filterNotNull()
    }
}
