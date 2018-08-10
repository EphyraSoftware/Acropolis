package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.DatastorePersistence
import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.service.api.IDatastore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Datastore : IDatastore {

    @Autowired
    private lateinit var persistence: DatastorePersistence

     override fun create(projectId: Int, name: String) {
        val datastore = DatastoreEntity()
        datastore.name = name
        
        val project = ProjectEntity()
        project.id = projectId
        datastore.project = project
        persistence.create(datastore)
    }
} 
