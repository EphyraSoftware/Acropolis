package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity

interface INetworkService {
    /**
     * Creates a new entity, to be associated with the given project
     * @param projectId the name of the project to find and associate this entity with
     * @param name the name of the entity to create
     */
    fun create(name: String, projectName: String)

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    fun get(name: String, projectId: Long): NetworkEntity?

    fun linkDatastore(networkId: Long, datastoreName: String, projectId: Long)

    fun linkApplicationSoftware(networkId: Long, applicationSoftwareName: String, projectId: Long)

    fun linkSystemSoftware(networkId: Long, systemSoftwareName: String, projectId: Long)
}
