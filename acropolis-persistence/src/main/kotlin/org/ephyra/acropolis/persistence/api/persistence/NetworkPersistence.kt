package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.impl.NetworkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around NetworkRepository operations
 * */
@Component
class NetworkPersistence {
    @Autowired
    private lateinit var repo: NetworkRepository

    /**
     * Create a new record for the entity in the database
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: NetworkEntity) {
        repo.save(entity)
    }

    /**
     * Find an instance with the given ID
     * @param id The ID to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun find(networkId: Long, projectId: Long): NetworkEntity? {
        val entity = repo.findById(networkId)
        return if (entity.isPresent) entity.get() else null
    }

    /**
     * Find an instance with the given ID in the project with the given ID
     * @param name The name to try and find in the database
     * @param projectId The ID of the project that this entity is scoped to
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun findByName(name: String, projectId: Long): NetworkEntity? {
        val entity = repo.findByNameAndProjectId(name, projectId)
        return if (entity.isPresent) entity.get() else null
    }

    /**
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun updateGrouping(entity: NetworkEntity) {
        repo.save(entity)
    }
}
