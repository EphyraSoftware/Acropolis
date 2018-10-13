package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.persistence.api.persistence.ConnectionPersistence
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service for interactions and mutations around ConnectionEntity
 */
@Service
class ConnectionService : IConnectionService {
    @Autowired
    private lateinit var persistence: ConnectionPersistence

    @Autowired
    private lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    @Autowired
    private lateinit var applicationSoftwarePersistence: ApplicationSoftwarePersistence

    @Autowired
    private lateinit var datastorePersistence: DatastorePersistence

    override fun create(fromConnectable: IConnectable, toConnectable: IConnectable, connectionType: ConnectionType) {
        val connection = ConnectionEntity(
                fromConnectable.getConnectionId(),
                fromConnectable.getConnectionEndpointType(),
                toConnectable.getConnectionId(),
                toConnectable.getConnectionEndpointType(),
                connectionType.type
        )

        persistence.create(connection)
    }

    override fun getConnectionsFrom(fromConnectable: IConnectable): List<IConnectable> {
        val connections = persistence.getConnectionsFrom(
                fromConnectable.getConnectionId(),
                fromConnectable.getConnectionEndpointType()
        )

        val res = connections.map { connectionEntity ->
            when (ConnectionEndpointType.fromInt(connectionEntity.toEndpointType)) {
                ConnectionEndpointType.SYSTEM_SOFTWARE -> systemSoftwarePersistence.find(connectionEntity.toId)
                ConnectionEndpointType.APPLICATION_SOFTWARE ->
                    applicationSoftwarePersistence.find(connectionEntity.toId)
                ConnectionEndpointType.DATASTORE -> datastorePersistence.find(connectionEntity.toId)
                else -> {
                    val msg = "Connection entity with connection to unknown type [${connectionEntity.toEndpointType}]"
                    throw IllegalStateException(msg)
                }
            }
        }

        return res.filterNotNull()
    }
}
