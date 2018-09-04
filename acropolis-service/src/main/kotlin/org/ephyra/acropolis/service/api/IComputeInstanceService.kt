package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity

interface IComputeInstanceService {
    fun create(projectId: Long, name: String)
    fun find(name: String, projectId: Long): ComputeInstanceEntity?
}