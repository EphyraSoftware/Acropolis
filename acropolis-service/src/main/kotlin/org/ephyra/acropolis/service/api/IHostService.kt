package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.HostEntity

interface IHostService {
    fun create(name: String, hostedBy: String)
    fun delete(id: Long)
    fun list(): List<HostEntity>
    fun get(name: String): HostEntity
}
