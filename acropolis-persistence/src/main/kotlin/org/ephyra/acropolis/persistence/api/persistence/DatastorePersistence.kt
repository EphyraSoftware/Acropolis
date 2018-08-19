package org.ephyra.acropolis.persistence.api.persistence

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

    fun find(id: Long): DatastoreEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    fun findByName(name: String): DatastoreEntity? {
        val entity = repo.findByName(name)
        return if (entity.isPresent) entity.get() else null
    }
}
