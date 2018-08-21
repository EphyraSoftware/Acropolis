package org.ephyra.acropolis.service.api

interface INetworkService {
    fun create(name: String, projectName: String)

    fun linkDatastore(networkId: Long, datastoreName: String)

    fun linkApplicationSoftware(networkId: Long, applicationSoftwareId: Long)

    fun linkSystemSoftware(networkId: Long, systemSoftwareId: Long)
}
