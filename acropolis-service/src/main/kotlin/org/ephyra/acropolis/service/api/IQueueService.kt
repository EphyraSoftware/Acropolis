package org.ephyra.acropolis.service.api

/**
 * Service interface for interactions and mutations around QueueEntity
 */
interface IQueueService {
    /**
     * Creates a queue.
     *
     * @param baseSoftwareId the ID of the base SystemSofware to associate this entity with
     */
    fun create(baseSoftwareId: Long)
}
