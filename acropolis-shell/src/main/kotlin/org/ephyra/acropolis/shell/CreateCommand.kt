package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class CreateCommand {
    @Autowired
    private lateinit var appState: AppState

    @Autowired
    private lateinit var projectService: IProjectService

    @Autowired
    private lateinit var applicationSoftwareService: IApplicationSoftwareService

    @Autowired
    private lateinit var systemSoftwareService: ISystemSoftwareService

    @Autowired
    private lateinit var computeInstanceService: IComputeInstanceService

    @Autowired
    private lateinit var networkService: INetworkService

    @ShellMethod("Create an item")
    fun create(itemType: String, name: String) {
        when (itemType) {
            "project" -> createProject(name)
            "application-software" -> createApplicationSoftware(name)
            "system-software" -> createSystemSoftware(name)
            "compute-instance" -> createComputeInstance(name)
            "network" -> createNetwork(name)
            else -> println("Don't know how to create an item with type [$itemType]")
        }
    }

    private fun createSystemSoftware(name: String) {
        val project = appState.currentProject
        if (project != null) {
            systemSoftwareService.create(project.name, name)
        } else {
            println("No project selected")
        }
    }

    private fun createApplicationSoftware(name: String) {
        val project = appState.currentProject
        if (project != null) {
            applicationSoftwareService.create(project.name, name)
        } else {
            println("No project selected")
        }
    }

    private fun createComputeInstance(name: String) {
        val project = appState.currentProject
        if (project != null) {
            computeInstanceService.create(project.name, name)
        } else {
            println("No project selected")
        }
    }

    private fun createNetwork(name: String) {
        val project = appState.currentProject
        if (project != null) {
            networkService.create(name, project.name)
        } else {
            println("No project selected")
        }
    }

    private fun createProject(name: String) {
        projectService.create(name)
    }

}