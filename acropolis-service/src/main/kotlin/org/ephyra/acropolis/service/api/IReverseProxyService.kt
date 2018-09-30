package org.ephyra.acropolis.service.api

/**
 * Service interface for interactions and mutations around ReverseProxyEntity
 * */
interface IReverseProxyService {
    /**
     * @param baseSoftwareId the ID of the base SystemSofware to associate this entity with
     */
    fun create(baseSoftwareId: Long)
}
