package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.LoadBalancerEntity
import org.ephyra.acropolis.persistence.impl.LoadBalancerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around LoadBalancerRepository operations
 * */
@Component
class LoadBalancerPersistence {
    @Autowired
    private lateinit var repo: LoadBalancerRepository

    /**
     * Create a new record for the entity in the database
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: LoadBalancerEntity) {
        repo.save(entity)
    }
}
