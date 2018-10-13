package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.persistence.api.persistence.ReverseProxyPersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IReverseProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service for interactions and mutations around ReverseProxyEntity
 */
@Service
class ReverseProxyService @Autowired constructor(
        private val persistence: ReverseProxyPersistence,

        private val systemSoftwarePersistence: SystemSoftwarePersistence
) : IReverseProxyService {
    override fun create(baseSoftwareId: Long) {
        val systemSoftwareEntity = systemSoftwarePersistence.find(baseSoftwareId)
                ?: throw IllegalStateException("Cannot specialize system software because no system-software " +
                        "exists with id [$baseSoftwareId]")

        val reverseProxy = ReverseProxyEntity("Hello, World!")
        persistence.create(reverseProxy)

        systemSoftwareEntity.specialization = reverseProxy
        systemSoftwarePersistence.update(systemSoftwareEntity)
    }
}
