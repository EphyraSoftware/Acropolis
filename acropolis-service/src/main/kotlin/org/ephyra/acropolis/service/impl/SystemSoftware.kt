package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.SystemSoftwarePersistence
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.service.api.ISystemSoftware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SystemSoftware : ISystemSoftware {
    @Autowired
    private lateinit var persistence: SystemSoftwarePersistence

    override fun create(projectId: Int, name: String) {
        val systemSoftware = SystemSoftwareEntity()
        systemSoftware.name = name

        val project = ProjectEntity()
        project.id = projectId
        systemSoftware.project = project

        persistence.create(systemSoftware)
    }
}