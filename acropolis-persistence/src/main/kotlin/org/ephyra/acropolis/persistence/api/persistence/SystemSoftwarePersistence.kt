package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.persistence.impl.SystemSoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around SystemSoftwareRepository operations
 * */
@Component
class SystemSoftwarePersistence {
    @Autowired
    private lateinit var repo: SystemSoftwareRepository

    /**
     * Create a new record for the entity in the database
     * @param systemSoftware The newly created instance of this entity to be persisted to the database
     */
    fun create(systemSoftware: SystemSoftwareEntity) {
        repo.save(systemSoftware)
    }

    /**
     * Find an instance with the given ID
     * @param id The ID to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun find(id: Long): SystemSoftwareEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    /**
     * Find an instance with the given ID in the project with the given ID
     * @param name The name to try and find in the database
     * @param projectId The ID of the project that this entity is scoped to
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun findByName(name: String, projectId: Long): SystemSoftwareEntity? {
        val entity = repo.findByNameAndProjectId(name, projectId)
        return if (entity.isPresent) entity.get() else null
    }

    fun update(systemSoftware: SystemSoftwareEntity) {
        repo.save(systemSoftware)
    }
}
