package org.ephyra.acropolis.service.api

interface ILoadBalancerService {
    /**
     * Creates a new LoadBalancerEntity, to be associated with the given SystemSoftware ID
     * @param baseSoftwareId the ID of the base SystemSofware to associate this entity with
     */
    fun create(baseSoftwareId: Long)
}
