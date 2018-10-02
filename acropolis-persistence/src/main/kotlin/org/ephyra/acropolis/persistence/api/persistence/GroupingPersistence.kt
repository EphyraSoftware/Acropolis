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
     * @param entity the instance of the entity containing modified fields to be persisted to the database
     */
    fun update(entity: GroupingEntity) {
        repo.save(entity)
    }
}
