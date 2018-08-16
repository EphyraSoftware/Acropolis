package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareSpecializationEntity
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.persistence.api.persistence.ReverseProxyPersistence
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SystemSoftwareService : ISystemSoftwareService {
    @Autowired
    private lateinit var persistence: SystemSoftwarePersistence

    @Autowired
    private lateinit var reverseProxyPersistence: ReverseProxyPersistence

    override fun create(projectId: Int, name: String) {
        val project = ProjectEntity()
        project.id = projectId

        val systemSoftware = SystemSoftwareEntity(name, project)

        persistence.create(systemSoftware)
    }

    override fun specialize(systemSoftwareId: Long, specialization: SystemSoftwareSpecializationEntity) {
        val systemSoftwareEntity = persistence.find(systemSoftwareId)
            ?: throw IllegalStateException("Cannot specialize system software because no system software exists with id [$systemSoftwareId]")

        when (specialization) {
            ReverseProxyEntity::class -> reverseProxyPersistence.create(specialization as ReverseProxyEntity)
            else -> throw IllegalStateException("Unknown specialization")
        }

        systemSoftwareEntity.specialization = specialization

        persistence.updateSpecialisation(systemSoftwareEntity)
    }
}
