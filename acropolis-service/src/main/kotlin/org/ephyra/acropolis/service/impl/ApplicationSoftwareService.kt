package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ApplicationSoftwareService : IApplicationSoftwareService {
    @Autowired
    private lateinit var persistence: ApplicationSoftwarePersistence

    /**
     * @param projectId the ID of the project to associate this entity with
     * @param name the name of the entity to create
     * */
    override fun create(projectId: Long, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val applicationSoftware = ApplicationSoftwareEntity(name, project)
        applicationSoftware.name = name

        persistence.create(applicationSoftware)
    }

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     * */
    override fun find(name: String, projectId: Long): ApplicationSoftwareEntity? {
        return persistence.findByName(name, projectId)
    }
}
