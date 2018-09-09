package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.impl.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProjectPersistence {
    @Autowired
    private lateinit var repo: ProjectRepository

    /**
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: ProjectEntity) {
        repo.save(entity)
    }

    /**
     * @param id the ID of the model to be deleted
     */
    fun delete(id: Long) {
        repo.deleteById(id)
    }

    /**
     * @return The entire List<> of entities of this type
     */
    fun getAll(): List<ProjectEntity> {
        return repo.findAll().toList()
    }

    /**
     * @param id The ID to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun find(id: Long): ProjectEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    /**
     * @param name The name to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun findByName(name: String): ProjectEntity? {
        val entity = repo.findByName(name)
        return if (entity.isPresent) entity.get() else null
    }
}
