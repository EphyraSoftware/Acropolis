package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.impl.ProjectEntity

interface IProject {
    fun createProject(name: String)
    fun listProjects(): List<ProjectEntity>
}
