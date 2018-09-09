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

    /**
     * Creates a new connection between two entities
     * @param fromConnectable The instance of a Connectable type to create the connection from
     * @param toConnectable The instance of a Connectable type to create the connection to
     * @param connectionType The type of connection to create, i.e. HostedBy, TalksTo
     */
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

    /**
     * Lists all the connections starting from the passed entity.
     * @param fromConnectable The instance of a Connectanle type to get all connections from
     * @return A list of all instances that have connections from the passed entity
     */
    override fun getConnectionsFrom(fromConnectable: IConnectable): List<IConnectable> {
        val connections = persistence.getConnectionsFrom(fromConnectable.getConnectionId(), fromConnectable.getConnectionEndpointType())

        val res = connections.map { connectionEntity ->
            when (ConnectionEndpointType.fromInt(connectionEntity.toEndpointType)) {
                ConnectionEndpointType.SYSTEM_SOFTWARE -> systemSoftwarePersistence.find(connectionEntity.toId)
                ConnectionEndpointType.APPLICATION_SOFTWARE -> applicationSoftwarePersistence.find(connectionEntity.toId)
                ConnectionEndpointType.DATASTORE -> datastorePersistence.find(connectionEntity.toId)
                else -> throw IllegalStateException("Connection entity with connection to unknown type [${connectionEntity.toEndpointType}")
            }
        }

        return res.filterNotNull()
    }
}
