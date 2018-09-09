package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity

interface IDatastoreService {
    /**
     * Creates a new entity, to be associated with the given project
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    fun create(name: String, projectName: String)

    /**
     * Find an instance with the given name
     * @param name the name of the entity to try and find
     * @param projectId the id of the project the find should be scoped to
     * @return an instance of the entity if found, or nil
     */
    fun get(name: String, projectId: Long): DatastoreEntity?
}
