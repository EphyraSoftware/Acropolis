package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.ProjectPersistence
import org.ephyra.acropolis.service.api.IProject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Project : IProject {
    @Autowired
    private lateinit var persistence: ProjectPersistence

    override fun createProject(name: String) {
        println("Acropolis service is creating project with name $name")
        val project = ProjectEntity()
        project.name = name
        persistence.create(project)
    }

    override fun listProjects(): List<ProjectEntity> {
        return persistence.getProjects()
    }

    override fun getProject(name: String): ProjectEntity {
        return persistence.getProject(name)
    }
}
