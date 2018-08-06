package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.impl.ProjectEntity
import org.ephyra.acropolis.persistence.impl.ProjectPersistence
import org.ephyra.acropolis.service.api.IProject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Project : IProject {
    @Autowired
    private lateinit var repo: ProjectPersistence

    override fun createProject(name: String) {
        println("Acropolis service is creating project with name $name")
        val project = ProjectEntity()
        project.name = name
        repo.create(project)
    }

    override fun listProjects(): List<ProjectEntity> {
        return repo.getProjects()
    }
}
