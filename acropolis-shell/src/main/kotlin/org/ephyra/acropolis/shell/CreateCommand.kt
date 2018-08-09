package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IApplicationSoftware
import org.ephyra.acropolis.service.api.IProject
import org.ephyra.acropolis.service.api.ISystemSoftware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class CreateCommand {
    @Autowired
    private lateinit var appState: AppState

    @Autowired
    private lateinit var project : IProject

    @Autowired
    private lateinit var applicationSoftware: IApplicationSoftware

    @Autowired
    private lateinit var systemSoftware: ISystemSoftware

    @ShellMethod("Create an item")
    fun create(itemType: String, name: String) {
        when (itemType) {
            "project" -> createProject(name)
            "application-software" -> createApplicationSoftware(name)
            "system-software" -> createSystemSoftware(name)
            else -> println("Don't know how to create an item with type [$itemType]")
        }
    }

    private fun createSystemSoftware(name: String) {
        val project = appState.currentProject
        if (project != null) {
            systemSoftware.create(project.id, name)
        }
        else {
            println("No project selected")
        }
    }

    private fun createApplicationSoftware(name: String) {
        val project = appState.currentProject
        if (project != null) {
            applicationSoftware.create(project.id, name)
        }
        else {
            println("No project selected")
        }
    }

    private fun createProject(name: String) {
        project.createProject(name)
    }
}