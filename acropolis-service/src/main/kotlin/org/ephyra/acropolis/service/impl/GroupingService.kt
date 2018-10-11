package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.api.persistence.GroupingPersistence
import org.ephyra.acropolis.service.api.IGroupingService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Service for interactions and mutations around GroupingEntity
 * */
@Component
class GroupingService : IGroupingService {
    private val Logger = LoggerFactory.getLogger(GroupingService::class.java)

    @Autowired
    private lateinit var persistence: GroupingPersistence

    @Autowired
    private lateinit var projectService: IProjectService

    /**
     * Creates a new entity, to be associated with the given project
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    override fun create(name: String, projectName: String) {
        val project = projectService.find(projectName)

        if (project == null) {
            Logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val entity = GroupingEntity(name, project)
        persistence.create(entity)
    }

    /**
     * Find and delete a record with the given ID, if found
     * @param id the id of the record to find and delete
     */
    override fun delete(id: Long) {
        Logger.trace("Acropolis service is deleting project #$id")

        persistence.delete(id)
    }

    /**
     * Return a List of all known records
     * @return A list of all existing records
     */
    override fun list(): List<GroupingEntity> {
        Logger.trace("Fetching list of groupings")

        return persistence.getAll()
    }

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    override fun find(name: String, projectId: Long): GroupingEntity? {
        return persistence.findByName(name, projectId)
    }
}
