package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IConnectionService
import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.util.function.Consumer

@ShellComponent
class ListCommand {
    @Autowired
    private lateinit var projectService : IProjectService

    @Autowired
    private lateinit var connectionService: IConnectionService

    @Autowired
    private lateinit var connectHelper: ConnectHelper

    @ShellMethod("List items by type")
    fun list(itemType: String, @ShellOption(defaultValue = "", arity = 3) rest: List<String>) {
        when (itemType) {
            "project" -> listProjects()
            "connection" -> listConnections(rest)
            else -> println("Don't know how to list items with type [$itemType]")
        }
    }

    private fun listProjects() {
        val result = projectService.listProjects()
        for (r in result) {
            println(r.name)
        }
    }

    private fun listConnections(args: List<String>) {
        if (args.size < 3) {
            println("Usage: list connection from <type> <id>")
            return
        }
        when (args[0]) {
            "from" -> listConnectionsFrom(args[2], args[1])
            else -> println("Don't understand arg [${args[0]}, must be one of 'from', 'to'")
        }
    }

    private fun listConnectionsFrom(id: String, type: String) {
        val fromConnectable = connectHelper.typenameToConnectable(id, type)

        val connections = connectionService.getConnectionsFrom(fromConnectable)

        connections.forEach {it ->
            println("Connection to [${it.getConnectionType()}, ${it.getConnectionType()}]")
        }
    }
}