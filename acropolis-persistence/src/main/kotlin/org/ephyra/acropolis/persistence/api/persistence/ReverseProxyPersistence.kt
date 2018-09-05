package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.persistence.impl.ReverseProxyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReverseProxyPersistence {
    @Autowired
    private lateinit var repo: ReverseProxyRepository
    /**
     * @param entity The newly created instance of this entity to be persisted to the database
     * */
    fun create(entity: ReverseProxyEntity) {
        repo.save(entity)
    }
}
