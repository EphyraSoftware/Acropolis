package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity

interface ISystemSoftwareService {
    fun create(projectId: Long, hostId : Int, name: String)
    fun get(name: String): SystemSoftwareEntity?
}
