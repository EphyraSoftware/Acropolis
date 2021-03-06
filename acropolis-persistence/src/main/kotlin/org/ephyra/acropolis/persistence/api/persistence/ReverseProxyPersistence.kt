package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.persistence.impl.ReverseProxyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around ReverseProxyRepository operations
 * */
@Component
class ReverseProxyPersistence {
    @Autowired
    private lateinit var repo: ReverseProxyRepository

    /**
     * Create a new record for the entity in the database
     * @param entity The newly created instance of this entity to be persisted to the database
     */
    fun create(entity: ReverseProxyEntity) {
        repo.save(entity)
    }
}
