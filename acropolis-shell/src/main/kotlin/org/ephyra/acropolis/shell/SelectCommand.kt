package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class SelectCommand {
    @Autowired
    private lateinit var appState: AppState

    @Autowired
    private lateinit var projectService: IProjectService

    @ShellMethod("Select an item")
    fun select(itemType: String, value: String) {
        when (itemType) {
            "project" -> selectProject(value)
        }
    }

    private fun selectProject(name: String) {
        appState.currentProject = projectService.get(name)
    }
}
