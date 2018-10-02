package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity

/**
 * Service for working with network model data
 */
interface INetworkService {
    /**
     * Creates a new entity, to be associated with the given project
     * @param name the name of the entity to create
     * @param projectName the name of the project to find and associate this entity with
     */
    fun create(name: String, projectName: String)

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    fun find(name: String, projectId: Long): NetworkEntity?

    /**
     * Include a compute instance in the specified network.
     *
     * @param networkId The id of the network to link to
     * @param computeInstanceName The name of the compute instance to be linked
     * @param projectId The parent project for the network and datastore
     */
    fun linkComputeInstance(networkId: Long, computeInstanceName: String, projectId: Long)
}
