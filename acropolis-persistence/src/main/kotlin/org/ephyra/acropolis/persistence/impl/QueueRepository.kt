package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.QueueEntity
import org.springframework.data.repository.CrudRepository

internal interface QueueRepository : CrudRepository<QueueEntity, Long>