package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity

/**
 * Service interface for interactions and mutations around ComputeInstanceEntity
 * */
interface IComputeInstanceService {
    /**
     * Creates a new entity, to be associated with the given project
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    fun create(name: String, projectName: String)

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    fun find(name: String, projectId: Long): ComputeInstanceEntity?

    /**
     * Include an application software in the specified network.
     *
     * @param computeInstanceId The id of the computeInstance to link to
     * @param applicationSoftwareName The name of the application software to be linked
     * @param projectId The parent project for the application software
     */
    fun linkApplicationSoftware(computeInstanceId: Long, applicationSoftwareName: String, projectId: Long)

    /**
     * Include a system software in the specified network.
     *
     * @param computeInstanceId The id of the computeInstance to link to
     * @param systemSoftwareName The name of the system software to be linked
     * @param projectId The parent project for the system software
     */
    fun linkSystemSoftware(computeInstanceId: Long, systemSoftwareName: String, projectId: Long)
}