package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.QueueEntity
import org.springframework.data.repository.CrudRepository

/**
 * Spring Repository for persisting QueueEntity
 * */
internal interface QueueRepository : CrudRepository<QueueEntity, Long>