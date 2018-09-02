package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity

interface IProjectService {
    fun create(name: String)
    fun delete(id: Long)
    fun list(): List<ProjectEntity>
    fun get(name: String): ProjectEntity?
}
