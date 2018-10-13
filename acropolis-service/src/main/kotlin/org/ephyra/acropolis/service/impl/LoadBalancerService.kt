package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.LoadBalancerEntity
import org.ephyra.acropolis.persistence.api.persistence.LoadBalancerPersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.ILoadBalancerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service for interactions and mutations around LoadBalancerEntity
 */
@Service
class LoadBalancerService : ILoadBalancerService {
    @Autowired
    private lateinit var persistence: LoadBalancerPersistence

    @Autowired
    private lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    override fun create(baseSoftwareId: Long) {
        val systemSoftwareEntity = systemSoftwarePersistence.find(baseSoftwareId)
                ?: throw IllegalStateException("Cannot specialize system software because no " +
                        "system-software exists with id [$baseSoftwareId]")

        val loadBalancer = LoadBalancerEntity("Hello, World!")
        persistence.create(loadBalancer)

        systemSoftwareEntity.specialization = loadBalancer
        systemSoftwarePersistence.update(systemSoftwareEntity)
    }

}
