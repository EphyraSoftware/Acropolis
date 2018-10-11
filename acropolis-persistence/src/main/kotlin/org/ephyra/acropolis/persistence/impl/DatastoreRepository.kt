package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Repository for persisting DatastoreEntity
 */
internal interface DatastoreRepository : CrudRepository<DatastoreEntity, Long>
