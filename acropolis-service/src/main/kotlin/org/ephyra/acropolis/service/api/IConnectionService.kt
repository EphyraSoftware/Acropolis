package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable

interface IConnectionService {
    fun create(fromConnectable: IConnectable, toConnectable: IConnectable, connectionType: ConnectionType)

    fun getConnectionsFrom(fromConnectable: IConnectable): List<IConnectable>
}
