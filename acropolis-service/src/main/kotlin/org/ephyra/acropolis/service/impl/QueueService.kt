package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.QueueEntity
import org.ephyra.acropolis.persistence.api.persistence.QueuePersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IQueueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QueueService : IQueueService {
    @Autowired
    private lateinit var persistence: QueuePersistence

    @Autowired
    private lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    /**
     * Creates a new entity, to be associated with the given SystemSoftware ID
     * @param baseSoftwareId the ID of the base SystemSofware to associate this entity with
     */
    override fun create(baseSoftwareId: Long) {
        val systemSoftwareEntity = systemSoftwarePersistence.find(baseSoftwareId)
                ?: throw IllegalStateException("Cannot specialize system software because no system software exists with id [$baseSoftwareId]")

        val queue = QueueEntity("Hello, World!")
        persistence.create(queue)

        systemSoftwareEntity.specialization = queue
        systemSoftwarePersistence.update(systemSoftwareEntity)
    }
}
