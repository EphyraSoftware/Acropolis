package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity

/**
 * Service interface for interactions and mutations around ApplicationSoftwareEntity
 */
interface IApplicationSoftwareService {

    /**
     * Creates a new entity, to be associated with the given project.
     *
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    fun create(name: String, projectName: String)

    /**
     * Find an instance with the given name that exists within the scope of the given project ID.
     *
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    fun find(name: String, projectId: Long): ApplicationSoftwareEntity?

    /**
     * Update an application software instance by saving the changes which have been made.
     * to the managed entity.
     *
     * @param applicationSoftware The application to update
     */
    fun update(applicationSoftware: ApplicationSoftwareEntity)
}
