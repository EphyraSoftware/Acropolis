package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity

interface IApplicationSoftwareService {
    /**
     * Creates a new entity, to be associated with the given project ID
     * @param projectId the ID of the project to associate this entity with
     * @param name the name of the entity to create
     */
    fun create(projectId: Long, name: String)

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    fun find(name: String, projectId: Long): ApplicationSoftwareEntity?
}