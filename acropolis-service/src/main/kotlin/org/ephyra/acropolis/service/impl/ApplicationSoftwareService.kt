package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ApplicationSoftwareService : IApplicationSoftwareService {
    val logger: Logger = LoggerFactory.getLogger(ApplicationSoftwareService::class.java)

    @Autowired
    private lateinit var persistence: ApplicationSoftwarePersistence

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
            logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val entity = ApplicationSoftwareEntity(name, project)
        persistence.create(entity)
    }

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    override fun find(name: String, projectId: Long): ApplicationSoftwareEntity? {
        return persistence.findByName(name, projectId)
    }
}
