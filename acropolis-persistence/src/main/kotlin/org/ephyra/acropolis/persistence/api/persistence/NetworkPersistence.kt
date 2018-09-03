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

    fun getNetwork(networkId: Long, projectId: Long): NetworkEntity? {
        val entity = repo.findById(networkId)
        return if (entity.isPresent) entity.get() else null
    }

    fun findByName(name: String, projectId: Long): NetworkEntity? {
        val entity = repo.findByNameAndProjectId(name, projectId)
        return if (entity.isPresent) entity.get() else null
    }

    fun updateGrouping(network: NetworkEntity) {
        repo.save(network)
    }
}
