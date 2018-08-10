package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.springframework.data.repository.CrudRepository

internal interface DatastoreRepository : CrudRepository<DatastoreEntity, Long>
