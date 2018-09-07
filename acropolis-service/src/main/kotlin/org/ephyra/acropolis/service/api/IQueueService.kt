package org.ephyra.acropolis.service.api

interface IQueueService {
    /**
     * @param baseSoftwareId the ID of the base SystemSofware to associate this entity with
     * */
    fun create(baseSoftwareId: Long)
}
