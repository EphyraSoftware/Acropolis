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
 */
@Component
class GroupingService : IGroupingService {
    private val logger = LoggerFactory.getLogger(GroupingService::class.java)

    @Autowired
    private lateinit var persistence: GroupingPersistence

    @Autowired
    private lateinit var projectService: IProjectService

    override fun create(name: String, projectName: String) {
        val project = projectService.find(projectName)

        if (project == null) {
            logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val entity = GroupingEntity(name, project)
        persistence.create(entity)
    }

    override fun delete(id: Long) {
        logger.trace("Acropolis service is deleting project #$id")

        persistence.delete(id)
    }

    override fun list(): List<GroupingEntity> {
        logger.trace("Fetching list of groupings")

        return persistence.getAll()
    }

    override fun find(name: String, projectId: Long): GroupingEntity? {
        return persistence.findByName(name, projectId)
    }
}
