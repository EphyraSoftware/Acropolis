package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Repository for persisting DatastoreEntity
 * */
internal interface DatastoreRepository : CrudRepository<DatastoreEntity, Long> {
    /**
     * @param name The name of the entity to find
     * @param projectId the ID of the project to scope the find to
     * @return An instance of DatastoreEntity if one is found
     */
    fun findByNameAndProjectId(name: String, projectId: Long): Optional<DatastoreEntity>
}
