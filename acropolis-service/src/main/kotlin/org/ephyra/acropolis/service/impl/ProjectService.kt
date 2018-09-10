package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ProjectPersistence
import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProjectService : IProjectService {
    @Autowired
    private lateinit var persistence: ProjectPersistence

    /**
     * Creates a new entity with the given name
     * @param name the name of the entity to create
     */
    override fun create(name: String) {
        println("Acropolis service is creating project with name $name")
        val project = ProjectEntity()
        project.name = name
        persistence.create(project)
    }

    /**
     * Find and delete a record with the given ID, if found
     * @param id the id of the record to find and delete
     */
    override fun delete(id: Long) {
        println("Acropolis service is deleting project #$id")
        persistence.delete(id)
    }

    /**
     * Return a List of all known records
     * @return A list of all existing records
     */
    override fun list(): List<ProjectEntity> {
        return persistence.getAll()
    }

    /**
     * Find an instance with the given name
     * @param name the name of the entity to try and find
     * @return an instance of the entity if found, or nil
     */
    override fun get(name: String): ProjectEntity? {
        return persistence.findByName(name)
    }
}
