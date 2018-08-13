package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class ListCommand {
    @Autowired
    private lateinit var projectService : IProjectService

    @ShellMethod("List items by type")
    fun list(itemType: String) {
        when (itemType) {
            "project" -> listProjects()
            else -> println("Don't know how to list items with type [$itemType]")
        }
    }

    private fun listProjects() {
        val result = projectService.listProjects()
        for (r in result) {
            println(r.name)
        }
    }
}