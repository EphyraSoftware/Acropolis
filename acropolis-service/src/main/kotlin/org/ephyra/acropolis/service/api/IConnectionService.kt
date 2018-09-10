package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable

interface IConnectionService {
    /**
     * Creates a new connection between two entities
     * @param fromConnectable The instance of a Connectable type to create the connection from
     * @param toConnectable The instance of a Connectable type to create the connection to
     * @param connectionType The type of connection to create, i.e. HostedBy, TalksTo
     */
    fun create(fromConnectable: IConnectable, toConnectable: IConnectable, connectionType: ConnectionType)

    /**
     * Lists all the connections starting from the passed entity.
     * @param fromConnectable The instance of a Connectanle type to get all connections from
     * @return A list of all instances that have connections from the passed entity
     */
    fun getConnectionsFrom(fromConnectable: IConnectable): List<IConnectable>
}
