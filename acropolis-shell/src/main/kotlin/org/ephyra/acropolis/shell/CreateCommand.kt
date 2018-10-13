package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.api.IGroupingService
import org.ephyra.acropolis.service.api.INetworkService
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

/**
 * Command for creating new items.
 */
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
    private lateinit var groupingService: IGroupingService

    @Autowired
    private lateinit var networkService: INetworkService

    /**
     * Handler for the create command.
     */
    @ShellMethod("Create an item")
    fun create(itemType: String, name: String) {
        when (itemType) {
            "project" -> createProject(name)
            "application-software" -> createApplicationSoftware(name)
            "system-software" -> createSystemSoftware(name)
            "compute-instance" -> createComputeInstance(name)
            "grouping" -> createGrouping(name)
            "network" -> createNetwork(name)
            else -> println("Don't know how to create an item with type [$itemType]")
        }
    }

    private fun createSystemSoftware(name: String) {
        val project = appState.currentProject
        if (project != null) {
            systemSoftwareService.create(name, project.name)
        } else {
            println("No project selected")
        }
    }

    private fun createApplicationSoftware(name: String) {
        val project = appState.currentProject
        if (project != null) {
            applicationSoftwareService.create(name, project.name)
        } else {
            println("No project selected")
        }
    }

    private fun createGrouping(name: String) {
        val project = appState.currentProject
        if (project != null) {
            groupingService.create(name, project.name)
        } else {
            println("No project selected")
        }
    }

    private fun createComputeInstance(name: String) {
        val project = appState.currentProject
        if (project != null) {
            computeInstanceService.create(name, project.name)
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
