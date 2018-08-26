package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.api.entity.HostEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.service.api.IDatastoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DatastoreService : IDatastoreService {

    @Autowired
    private lateinit var persistence: DatastorePersistence

    override fun create(projectId: Long, hostId: Int, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val host = HostEntity()
        host.id = hostId

        val datastore = DatastoreEntity(name, project, host)
        datastore.name = name

        persistence.create(datastore)
    }

    override fun get(name: String): DatastoreEntity? {
        return persistence.findByName(name)
    }
} 
