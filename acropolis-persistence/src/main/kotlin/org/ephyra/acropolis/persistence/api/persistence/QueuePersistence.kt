package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.QueueEntity
import org.ephyra.acropolis.persistence.impl.QueueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QueuePersistence {
    @Autowired
    private lateinit var repo: QueueRepository

    /**
     * @param entity The newly created instance of this entity to be persisted to the database
     * */
    fun create(entity: QueueEntity) {
        repo.save(entity)
    }

    /**
     * @return The entire List<> of entities of this type
     * */
    fun getAll(): List<QueueEntity> {
        return repo.findAll().toList()
    }
}
