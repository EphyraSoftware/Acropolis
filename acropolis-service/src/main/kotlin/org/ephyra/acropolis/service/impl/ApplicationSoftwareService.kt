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

    override fun create(projectId: Long, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val applicationSoftware = ApplicationSoftwareEntity(name, project)
        applicationSoftware.name = name

        persistence.create(applicationSoftware)
    }
}
