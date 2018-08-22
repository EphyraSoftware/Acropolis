package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.QueueEntity
import org.ephyra.acropolis.persistence.impl.QueueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QueuePersistence {
    @Autowired
    private lateinit var repo: QueueRepository

    fun create(queue: QueueEntity) {
        repo.save(queue)
    }

    fun getQueues(): List<QueueEntity> {
        return repo.findAll().toList()
    }
}
