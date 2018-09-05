package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface DatastoreRepository : CrudRepository<DatastoreEntity, Long> {
<<<<<<< HEAD
    fun findByNameAndProjectId(name: String, projectId: Long): Optional<DatastoreEntity>
=======
    /**
     * @param name The name of the entity to find
     * @return An instance of DatastoreEntity if one is found
     * */
    fun findByName(name: String): Optional<DatastoreEntity>
>>>>>>> Persistence Layer Doc-Comments #40
}
