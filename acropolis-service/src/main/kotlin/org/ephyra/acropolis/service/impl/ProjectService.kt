package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ProjectPersistence
import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProjectService : IProjectService {
    @Autowired
    private lateinit var persistence: ProjectPersistence

    override fun create(name: String) {
        println("Acropolis service is creating project with name $name")
        val project = ProjectEntity()
        project.name = name
        persistence.create(project)
    }

    override fun delete(id: Long) {
        println("Acropolis service is deleting project #$id")
        persistence.delete(id)
    }

    override fun list(): List<ProjectEntity> {
        return persistence.getAll()
    }

    override fun get(name: String): ProjectEntity? {
        return persistence.findByName(name)
    }
}
