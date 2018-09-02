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
    private lateinit var hostService: IHostService

    @Autowired
    private lateinit var applicationSoftwareService: IApplicationSoftwareService

    @Autowired
    private lateinit var systemSoftwareService: ISystemSoftwareService

    @Autowired
    private lateinit var datastoreService: IDatastoreService

    @ShellMethod("Create an item")
    fun create(itemType: String, name: String, hostedBy: String = "") {
        when (itemType) {
            "project" -> createProject(name)
            "host" -> createHost(name, hostedBy)
            "application-software" -> createApplicationSoftware(name, hostedBy)
            "system-software" -> createSystemSoftware(name, hostedBy)
            "datastore" -> createDatastore(name, hostedBy)
            else -> println("Don't know how to create an item with type [$itemType]")
        }
    }

    private fun createSystemSoftware(name: String, hostname: String) {
        val project = appState.currentProject
        if (project != null) {
            systemSoftwareService.create(project.id, hostname, name)
        } else {
            println("No project selected")
        }
    }

    private fun createApplicationSoftware(name: String, hostname: String) {
        val project = appState.currentProject
        if (project != null) {
            applicationSoftwareService.create(project.id, hostname, name)
        } else {
            println("No project selected")
        }
    }

    private fun createDatastore(name: String, hostname: String) {
        val project = appState.currentProject
        if (project != null) {
            datastoreService.create(project.id, hostname, name)
        } else {
            println("No project selected")
        }
    }

    private fun createProject(name: String) {
        projectService.create(name)
    }

    private fun createHost(name: String, hostedBy: String) {
        hostService.create(name, hostedBy)
    }
}