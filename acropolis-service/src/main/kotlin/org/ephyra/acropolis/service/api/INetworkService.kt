package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity

interface INetworkService {
    fun create(name: String, projectName: String)

    fun get(name: String): NetworkEntity?

    fun linkDatastore(networkId: Long, datastoreName: String)

    fun linkApplicationSoftware(networkId: Long, applicationSoftwareId: Long)

    fun linkSystemSoftware(networkId: Long, systemSoftwareId: Long)
}
