package org.ephyra.acropolis.persistence.api

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.impl.DatastoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DatastorePersistence {
    @Autowired
    private lateinit var repo: DatastoreRepository
     fun create(datastore: DatastoreEntity) {
        repo.save(datastore)
    }
} 
