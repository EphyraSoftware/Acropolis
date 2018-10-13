package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

/**
 * Command for selecting an item.
 *
 * This will load the identified item for use in a later command.
 * For example, most commands require a project to be selected.
 */
@ShellComponent
class SelectCommand {
    @Autowired
    private lateinit var appState: AppState

    @Autowired
    private lateinit var projectService: IProjectService

    /**
     * Handler for the select command.
     */
    @ShellMethod("Select an item")
    fun select(itemType: String, value: String) {
        when (itemType) {
            "project" -> selectProject(value)
        }
    }

    private fun selectProject(name: String) {
        appState.currentProject = projectService.find(name)
    }
}
