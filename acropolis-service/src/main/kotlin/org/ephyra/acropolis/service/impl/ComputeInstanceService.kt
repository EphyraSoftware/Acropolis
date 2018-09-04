package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ComputeInstancePersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ComputeInstanceService : IComputeInstanceService {
    @Autowired
    private lateinit var persistence: ComputeInstancePersistence

    override fun create(projectId: Long, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val computeInstance = ComputeInstanceEntity(name, project)
        computeInstance.name = name

        persistence.create(computeInstance)
    }

    override fun find(name: String, projectId: Long): ComputeInstanceEntity? {
        return persistence.findByName(name, projectId)
    }
}
