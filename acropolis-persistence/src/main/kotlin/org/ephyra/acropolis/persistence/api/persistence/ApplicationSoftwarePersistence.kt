package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.impl.ApplicationSoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ApplicationSoftwarePersistence {
    @Autowired
    private lateinit var repo: ApplicationSoftwareRepository

    /**
     * Create a new record for the entity in the database
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: ApplicationSoftwareEntity) {
        repo.save(entity)
    }

    /**
     * @param id The ID to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun find(id: Long): ApplicationSoftwareEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    /**
     * Find an instance with the given ID in the project with the given ID
     * @param name The name to try and find in the database
     * @param projectId The ID of the project that this entity is scoped to
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun findByName(name: String, projectId: Long): ApplicationSoftwareEntity? {
        val entity = repo.findByNameAndProjectId(name, projectId)
        return if (entity.isPresent) entity.get() else null
    }
}
