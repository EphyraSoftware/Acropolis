package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity

interface IApplicationSoftwareService {
    fun create(projectId: Int, name: String)

    fun find(name: String, projectId: Long): ApplicationSoftwareEntity?
}