package org.ephyra.acropolis.persistence.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProjectPersistence {
    @Autowired
    private lateinit var repo: ProjectRepository

    fun create(project: ProjectEntity) {
        repo.save(project)
    }

    fun getProjects(): List<ProjectEntity> {
        return repo.findAll().toList()
    }
}
