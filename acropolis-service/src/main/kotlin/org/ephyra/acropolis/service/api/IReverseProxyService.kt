package org.ephyra.acropolis.service.api

/**
 * Service interface for interactions and mutations around ReverseProxyEntity
 */
interface IReverseProxyService {
    /**
     * Creates a reverse proxy.
     *
     * @param baseSoftwareId the ID of the base SystemSofware to associate this entity with
     */
    fun create(baseSoftwareId: Long)
}
