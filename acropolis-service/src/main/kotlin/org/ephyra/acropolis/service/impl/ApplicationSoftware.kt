package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.ApplicationSoftwarePersistence
import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.service.api.IApplicationSoftware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ApplicationSoftware : IApplicationSoftware {
    @Autowired
    private lateinit var persistence: ApplicationSoftwarePersistence

    override fun create(projectId: Int, name: String) {
        val applicationSoftware = ApplicationSoftwareEntity()
        applicationSoftware.name = name

        val project = ProjectEntity()
        project.id = projectId
        applicationSoftware.project = project

        persistence.create(applicationSoftware)
    }
}