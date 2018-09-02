package org.ephyra.acropolis.persistence.api.persistence

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

    fun delete(id: Long) {
        repo.deleteById(id)
    }

    fun getAll(): List<ProjectEntity> {
        return repo.findAll().toList()
    }

    fun find(id: Long): ProjectEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    fun findByName(name: String): ProjectEntity? {
        val entity = repo.findByName(name)
        return if (entity.isPresent) entity.get() else null
    }
}
