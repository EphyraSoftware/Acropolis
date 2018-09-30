package org.ephyra.acropolis.service.api

interface IDatastoreService {
    /**
     * Creates a new entity, to be associated with the given project
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    fun create(baseSoftwareId: Long)
}
