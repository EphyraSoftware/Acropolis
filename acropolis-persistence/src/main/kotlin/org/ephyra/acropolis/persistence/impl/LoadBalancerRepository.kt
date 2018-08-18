package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.LoadBalancerEntity
import org.springframework.data.repository.CrudRepository

interface LoadBalancerRepository : CrudRepository<LoadBalancerEntity, Long>
