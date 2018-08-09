package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity

interface IProject {
    fun createProject(name: String)
    fun listProjects(): List<ProjectEntity>
    fun getProject(name: String): ProjectEntity
}
