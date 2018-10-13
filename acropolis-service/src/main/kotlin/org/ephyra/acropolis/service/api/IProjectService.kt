package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity

/**
 * Service interface for interactions and mutations around ProjectEntity
 */
interface IProjectService {
    /**
     * Creates a new entity with the given name.
     *
     * @param name the name of the entity to create
     */
    fun create(name: String)

    /**
     * Find and delete a record with the given ID, if found.
     *
     * @param id the id of the record to find and delete
     */
    fun delete(id: Long)

    /**
     * Return a List of all known records.
     *
     * @return A list of all existing records
     */
    fun list(): List<ProjectEntity>

    /**
     * Find an instance with the given name.
     *
     * @param name the name of the entity to try and find
     * @return an instance of the entity if found, or nil
     */
    fun find(name: String): ProjectEntity?
}
