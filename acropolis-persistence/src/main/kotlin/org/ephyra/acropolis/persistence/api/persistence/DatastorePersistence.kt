package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.impl.DatastoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around DatastoreRepository operations
 * */
@Component
class DatastorePersistence {
    @Autowired
    private lateinit var repo: DatastoreRepository

    /**
     * Create a new record for the entity in the database
     * @param datastore The newly created instance of this entity to be persisted to the database
     */
    fun create(datastore: DatastoreEntity) {
        repo.save(datastore)
    }

    /**
     * Find an instance with the given ID
     * @param id The ID to try and find in the database
     * @return The entity corresponding to the specified ID, or null if not found
     */
    fun find(id: Long): DatastoreEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }
}
