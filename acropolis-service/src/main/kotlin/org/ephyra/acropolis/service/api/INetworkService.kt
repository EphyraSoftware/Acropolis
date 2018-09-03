package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity

interface INetworkService {
    fun create(name: String, projectName: String)

    fun get(name: String, projectId: Long): NetworkEntity?

    fun linkDatastore(networkId: Long, datastoreName: String, projectId: Long)

    fun linkApplicationSoftware(networkId: Long, applicationSoftwareName: String, projectId: Long)

    fun linkSystemSoftware(networkId: Long, systemSoftwareName: String, projectId: Long)
}
