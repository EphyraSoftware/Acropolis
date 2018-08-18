package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.persistence.api.persistence.ReverseProxyPersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IReverseProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReverseProxyService : IReverseProxyService {
    @Autowired
    private lateinit var persistence: ReverseProxyPersistence

    @Autowired
    private lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    override fun create(baseSoftwareId: Long) {
        val systemSoftwareEntity = systemSoftwarePersistence.find(baseSoftwareId)
            ?: throw IllegalStateException("Cannot specialize system software because no system software exists with id [$baseSoftwareId]")

        val reverseProxy = ReverseProxyEntity("Hello, World!")
        persistence.create(reverseProxy)

        systemSoftwareEntity.specialization = reverseProxy
        systemSoftwarePersistence.updateSpecialization(systemSoftwareEntity)
    }
}
