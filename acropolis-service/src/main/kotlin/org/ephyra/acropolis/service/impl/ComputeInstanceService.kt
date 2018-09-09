package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ComputeInstancePersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ComputeInstanceService : IComputeInstanceService {
    @Autowired
    private lateinit var persistence: ComputeInstancePersistence

    /**
     * @param projectId the ID of the project to associate this entity with
     * @param name the name of the entity to create
     */
    override fun create(projectId: Long, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val computeInstance = ComputeInstanceEntity(name, project)
        computeInstance.name = name

        persistence.create(computeInstance)
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
