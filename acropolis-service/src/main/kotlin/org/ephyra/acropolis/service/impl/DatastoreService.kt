package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IDatastoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service for interactions and mutations around DatastoreEntity
 * */
@Service
class DatastoreService @Autowired constructor(
        private val persistence: DatastorePersistence,

        private val systemSoftwarePersistence: SystemSoftwarePersistence
) : IDatastoreService {
    /**
     * Creates a new entity, to be associated with the given SystemSoftware ID
     * @param baseSoftwareId the ID of the base SystemSofware to associate this entity with
     */
    override fun create(baseSoftwareId: Long) {
        val systemSoftwareEntity = systemSoftwarePersistence.find(baseSoftwareId)
                ?: throw IllegalStateException("Cannot specialize system software because no system software exists with id [$baseSoftwareId]")

        val datastore = DatastoreEntity("Hello, World!")
        persistence.create(datastore)

        systemSoftwareEntity.specialization = datastore
        systemSoftwarePersistence.update(systemSoftwareEntity)
    }
}
