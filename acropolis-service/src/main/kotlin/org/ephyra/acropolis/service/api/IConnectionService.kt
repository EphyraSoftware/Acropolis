package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.IConnectable

interface IConnectionService {
    fun create(fromConnectable: IConnectable, toConnectable: IConnectable)

    fun getConnectionsFrom(fromConnectable: IConnectable): List<IConnectable>
}
