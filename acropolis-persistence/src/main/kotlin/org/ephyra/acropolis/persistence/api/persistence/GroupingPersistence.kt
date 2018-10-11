package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.impl.GroupingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around GroupingRepository operations
 * */
@Component
class GroupingPersistence {
    @Autowired
    private lateinit var repo: GroupingRepository


    /**
     * Create a new record for the entity in the database
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: GroupingEntity) {
        repo.save(entity)
    }

    /**
     * Updates an existing database record with the details contained within the entity parameter
     * @param id the ID of the model to be deleted
     */
    fun delete(id: Long) {
        repo.deleteById(id)
    }

    /**
     * Get all GroupingEntities
     * @return The entire List<> of entities of this type
     */
    fun getAll(): List<GroupingEntity> {
        return repo.findAll().toList()
    }

    /**
     * Find an instance with the given ID
     * @param id The ID to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun find(id: Long): GroupingEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    /**
     * Find an instance with the given ID in the project with the given ID
     * @param name The name to try and find in the database
     * @param projectId The ID of the project that this entity is scoped to
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun findByName(name: String, projectId: Long): GroupingEntity? {
        val entity = repo.findByNameAndProjectId(name, projectId)
        return if (entity.isPresent) entity.get() else null
    }
}
