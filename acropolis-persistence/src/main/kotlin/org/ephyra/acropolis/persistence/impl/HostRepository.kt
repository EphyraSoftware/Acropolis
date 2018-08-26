package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.HostEntity
import org.springframework.data.repository.CrudRepository

internal interface HostRepository : CrudRepository<HostEntity, Long> {
    fun findByName(name: String): HostEntity
}
