package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.impl.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around ProjectRepository operations
 * */
@Component
class ProjectPersistence {
    @Autowired
    private lateinit var repo: ProjectRepository

    /**
     * Create a new record for the entity in the database
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: ProjectEntity) {
        repo.save(entity)
    }

    /**
     * Delete the ProjectEntity with the ID specified
     * @param id the ID of the model to be deleted
     */
    fun delete(id: Long) {
        repo.deleteById(id)
    }

    /**
     * Get all ProjectEntities
     * @return The entire List<> of entities of this type
     */
    fun getAll(): List<ProjectEntity> {
        return repo.findAll().toList()
    }

    /**
     * Find an instance with the given ID
     * @param id The ID to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun find(id: Long): ProjectEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    /**
     * Find an instance with the given ID in the project with the given ID
     * @param name The name to try and find in the database
     * @param projectId The ID of the project that this entity is scoped to
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun findByName(name: String): ProjectEntity? {
        val entity = repo.findByName(name)
        return if (entity.isPresent) entity.get() else null
    }
}
