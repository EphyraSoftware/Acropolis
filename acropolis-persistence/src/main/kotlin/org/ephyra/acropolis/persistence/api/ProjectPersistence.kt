package org.ephyra.acropolis.persistence.api

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.impl.ProjectRepository
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
