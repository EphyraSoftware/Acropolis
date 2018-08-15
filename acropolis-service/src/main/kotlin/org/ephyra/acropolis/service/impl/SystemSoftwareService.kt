package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SystemSoftwareService : ISystemSoftwareService {
    @Autowired
    private lateinit var persistence: SystemSoftwarePersistence

    override fun create(projectId: Int, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val systemSoftware = SystemSoftwareEntity(name, project)

        persistence.create(systemSoftware)
    }
}
