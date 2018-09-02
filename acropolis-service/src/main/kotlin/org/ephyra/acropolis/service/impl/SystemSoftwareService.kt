package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.HostEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SystemSoftwareService : ISystemSoftwareService {
    @Autowired
    private lateinit var persistence: SystemSoftwarePersistence

    override fun create(projectId : Long, hostname: String?, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        var host: HostEntity? = null
        if (hostname != null) {
            val hostService = HostService()
            host = hostService.get(hostname)
        }

        val systemSoftware = SystemSoftwareEntity(name, project, host)

        persistence.create(systemSoftware)
    }

    override fun get(name: String): SystemSoftwareEntity? {
        return persistence.findByName(name)
    }
}
