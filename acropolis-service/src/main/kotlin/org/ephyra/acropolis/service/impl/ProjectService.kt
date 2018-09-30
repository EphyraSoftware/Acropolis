package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ProjectPersistence
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Service for interactions and mutations around ProjectEntity
 * */
@Component
class ProjectService : IProjectService {
    private val Logger = LoggerFactory.getLogger(ProjectService::class.java)

    @Autowired
    private lateinit var persistence: ProjectPersistence

    /**
     * Creates a new entity with the given name
     * @param name the name of the entity to create
     */
    override fun create(name: String) {
        Logger.trace("Acropolis service is creating project with name $name")

        val project = ProjectEntity()
        project.name = name
        persistence.create(project)
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
    override fun list(): List<ProjectEntity> {
        Logger.trace("Fetching list of projects")

        return persistence.getAll()
    }

    /**
     * Find an instance with the given name
     * @param name the name of the entity to try and find
     * @return an instance of the entity if found, or nil
     */
    override fun get(name: String): ProjectEntity? {
        Logger.trace("Getting project by name [$name]")

        return persistence.findByName(name)
    }
}
