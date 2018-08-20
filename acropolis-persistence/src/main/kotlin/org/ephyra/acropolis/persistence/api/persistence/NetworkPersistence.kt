package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.impl.NetworkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NetworkPersistence {
    @Autowired
    private lateinit var repo: NetworkRepository

    fun create(network: NetworkEntity) {
        repo.save(network)
    }
}
