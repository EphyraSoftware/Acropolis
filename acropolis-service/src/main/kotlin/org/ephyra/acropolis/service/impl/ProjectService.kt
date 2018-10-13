package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ProjectPersistence
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Service for interactions and mutations around ProjectEntity
 */
@Component
class ProjectService : IProjectService {
    private val logger = LoggerFactory.getLogger(ProjectService::class.java)

    @Autowired
    private lateinit var persistence: ProjectPersistence

    override fun create(name: String) {
        logger.trace("Acropolis service is creating project with name $name")

        val project = ProjectEntity()
        project.name = name
        persistence.create(project)
    }

    override fun delete(id: Long) {
        logger.trace("Acropolis service is deleting project #$id")

        persistence.delete(id)
    }

    override fun list(): List<ProjectEntity> {
        logger.trace("Fetching list of projects")

        return persistence.getAll()
    }

    override fun find(name: String): ProjectEntity? {
        logger.trace("Getting project by name [$name]")

        return persistence.findByName(name)
    }
}
