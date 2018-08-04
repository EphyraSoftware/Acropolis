package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IProject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class CreateCommand {
    @Autowired
    private lateinit var project : IProject

    @ShellMethod("Create an item")
    fun create(itemType: String, name: String) {
        when (itemType) {
            "project" -> createProject(name)
            else -> println("Don't know how to create an item with type [$itemType]")
        }
    }

    private fun createProject(name: String) {
        project.createProject(name)
    }
}