package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Repository for persisting NetworkEntity
 * */
interface NetworkRepository : CrudRepository<NetworkEntity, Long> {
    /**
     * @param name The name of the entity to find
     * @param projectId the ID of the project this name is scoped to
     * @return An instance of NetworkEntity if one is found
     */
    fun findByNameAndProjectId(name: String, projectId: Long): Optional<NetworkEntity>
}
