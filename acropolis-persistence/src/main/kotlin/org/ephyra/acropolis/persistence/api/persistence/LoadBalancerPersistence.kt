package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.LoadBalancerEntity
import org.ephyra.acropolis.persistence.impl.LoadBalancerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LoadBalancerPersistence {
    @Autowired
    private lateinit var repo: LoadBalancerRepository

    fun create(entity: LoadBalancerEntity) {
        repo.save(entity)
    }
}