package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface NetworkRepository : CrudRepository<NetworkEntity, Long> {
    fun findByName(name: String): Optional<NetworkEntity>
}
