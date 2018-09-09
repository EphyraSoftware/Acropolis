package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.impl.GroupingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GroupingPersistence {
    @Autowired
    private lateinit var repo: GroupingRepository

    /**
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: GroupingEntity) {
        repo.save(entity)
    }

    /**
     * @param entity the instance of the entity containing modified fields to be persisted to the database
     */
    fun update(entity: GroupingEntity) {
        repo.save(entity)
    }
}
