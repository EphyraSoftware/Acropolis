package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Service for interactions and mutations around SystemSoftwareEntity
 */
@Component
class SystemSoftwareService : ISystemSoftwareService {
    private val logger = LoggerFactory.getLogger(SystemSoftwareService::class.java)

    @Autowired
    private lateinit var persistence: SystemSoftwarePersistence

    @Autowired
    private lateinit var projectService: IProjectService

    override fun create(name: String, projectName: String) {
        val project = projectService.find(projectName)

        if (project == null) {
            logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val entity = SystemSoftwareEntity(name, project)
        persistence.create(entity)
    }

    override fun find(name: String, projectId: Long): SystemSoftwareEntity? {
        return persistence.findByName(name, projectId)
    }

    override fun findAll(projectName: String): List<SystemSoftwareEntity> {
        val project = projectService.find(projectName)
        if (project == null) {
            val msg = "Could not find project with name [$projectName]"
            logger.error(msg)
            throw IllegalStateException(msg)
        }

        return persistence.findAll(project.id)
    }

    override fun update(systemSoftware: SystemSoftwareEntity) {
        return persistence.update(systemSoftware)
    }
}
