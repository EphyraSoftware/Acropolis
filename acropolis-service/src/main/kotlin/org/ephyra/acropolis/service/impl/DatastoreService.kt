package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.service.api.IDatastoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DatastoreService : IDatastoreService {

    @Autowired
    private lateinit var persistence: DatastorePersistence

    /**
     * Creates a new entity, to be associated with the given project ID
     * @param projectId the ID of the project to associate this entity with
     * @param name the name of the entity to create
     */
    override fun create(projectId: Long, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val datastore = DatastoreEntity(name, project)
        datastore.name = name

        persistence.create(datastore)
    }

<<<<<<< HEAD
    override fun get(name: String, projectId: Long): DatastoreEntity? {
        return persistence.findByName(name, projectId)
=======
    /**
     * Find an instance with the given name
     * @param name the name of the entity to try and find
     * @return an instance of the entity if found, or nil
     */
    override fun get(name: String): DatastoreEntity? {
        return persistence.findByName(name)
>>>>>>> Service layer doc comments, for all create, list, find, delete operations
    }
} 
