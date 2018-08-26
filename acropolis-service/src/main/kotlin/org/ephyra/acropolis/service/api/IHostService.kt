package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.HostEntity

interface IHostService {
    fun createHost(name: String, hostedBy: String)
    fun deleteHost(id: Long)
    fun listHosts(): List<HostEntity>
    fun getHost(name: String): HostEntity
}
