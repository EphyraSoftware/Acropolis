package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity

/**
 * Service interface for interactions and mutations around GroupingEntity
 */
interface IGroupingService {
    /**
     * Creates a new entity, to be associated with the given project.
     *
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    fun create(name: String, projectName: String)

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
    fun list(): List<GroupingEntity>

    /**
     * Find an instance with the given name that exists within the scope of the given project ID.
     *
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    fun find(name: String, projectId: Long): GroupingEntity?
}
