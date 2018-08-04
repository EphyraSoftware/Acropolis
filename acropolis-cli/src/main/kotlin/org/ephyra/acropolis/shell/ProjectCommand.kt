package org.ephyra.acropolis.shell

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class ProjectCommand {
    @ShellMethod("Create a project")
    fun create(name: String) {
        println(name)
    }
}