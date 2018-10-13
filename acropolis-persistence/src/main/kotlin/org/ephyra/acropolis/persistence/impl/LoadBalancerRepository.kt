package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.LoadBalancerEntity
import org.springframework.data.repository.CrudRepository

/**
 * Spring Repository for persisting LoadBalancerEntity
 */
interface LoadBalancerRepository : CrudRepository<LoadBalancerEntity, Long>
