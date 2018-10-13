package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Service for interactions and mutations around ApplicationSoftwareEntity
 */
@Component
class ApplicationSoftwareService : IApplicationSoftwareService {
    val logger: Logger = LoggerFactory.getLogger(ApplicationSoftwareService::class.java)

    @Autowired
    private lateinit var persistence: ApplicationSoftwarePersistence

    @Autowired
    private lateinit var projectService: IProjectService

    override fun create(name: String, projectName: String) {
        val project = projectService.find(projectName)

        if (project == null) {
            logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val entity = ApplicationSoftwareEntity(name, project)
        persistence.create(entity)
    }

    override fun find(name: String, projectId: Long): ApplicationSoftwareEntity? {
        return persistence.findByName(name, projectId)
    }

    override fun update(applicationSoftware: ApplicationSoftwareEntity) {
        persistence.update(applicationSoftware)
    }
}
