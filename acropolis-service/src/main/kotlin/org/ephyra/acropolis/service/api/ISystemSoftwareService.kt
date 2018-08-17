package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareSpecializationEntity

interface ISystemSoftwareService {
    fun create(projectId: Int, name: String)
}
