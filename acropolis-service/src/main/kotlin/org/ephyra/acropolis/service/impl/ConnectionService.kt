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
import java.util.stream.Collector
import java.util.stream.Collectors

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

        if (connectionType === ConnectionType.HOSTED_BY && isCircularHostedBy(fromConnectable, toConnectable)) {
            throw IllegalStateException("Circular reference of HostedBy detected")
        }

        val connection = ConnectionEntity(
                fromConnectable.getConnectionId(),
                fromConnectable.getConnectionEndpointType(),
                toConnectable.getConnectionId(),
                toConnectable.getConnectionEndpointType(),
                connectionType.type
        )

        persistence.create(connection)
    }

    override fun getConnectionsFrom(fromConnectable: IConnectable, connectionType: ConnectionType): List<IConnectable> {
        val connections = persistence.getConnectionsFrom(
                fromConnectable.getConnectionId(),
                fromConnectable.getConnectionEndpointType()
        )

        val res = connections.stream().filter { connectionEntity ->
            connectionEntity.connectionType == connectionType.type
        }.map { connectionEntity ->
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
        }.collect(Collectors.toList())

        return res.filterNotNull()
    }

    override fun isCircularHostedBy(fromConnectable: IConnectable, toConnectable: IConnectable): Boolean {
        val connectionsOnToEntity = this.getConnectionsFrom(toConnectable, ConnectionType.HOSTED_BY)
        return connectionsOnToEntity.contains(fromConnectable) || connectionsOnToEntity.any { x -> this.isCircularHostedBy(fromConnectable, x) }
    }
}
