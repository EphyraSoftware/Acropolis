package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity
import org.ephyra.acropolis.persistence.api.persistence.ComputeInstancePersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ComputeInstanceService : IComputeInstanceService {
    val Logger = LoggerFactory.getLogger(ComputeInstanceService::class.java)

    @Autowired
    private lateinit var persistence: ComputeInstancePersistence

    @Autowired
    private lateinit var projectService: IProjectService

    /**
     * Creates a new entity, to be associated with the given project
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    override fun create(name: String, projectName: String) {
        val project = projectService.get(projectName)

        if (project == null) {
            Logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val entity = ComputeInstanceEntity(name, project)
        persistence.create(entity)
    }

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    override fun find(name: String, projectId: Long): ComputeInstanceEntity? {
        return persistence.findByName(name, projectId)
    }
}
