package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.service.api.IConnectionService
import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

const val EXTRA_PARAM_COUNT = 3

/**
 * Command for listing items.
 *
 * For example, you can list all projects.
 */
@ShellComponent
class ListCommand {
    @Autowired
    private lateinit var projectService: IProjectService

    @Autowired
    private lateinit var connectionService: IConnectionService

    @Autowired
    private lateinit var connectHelper: ConnectHelper

    /**
     * Handler for the list command.
     */
    @ShellMethod("List items by type")
    fun list(itemType: String, @ShellOption(defaultValue = "", arity = 3) rest: List<String>) {
        when (itemType) {
            "project" -> listProjects()
            "connection" -> listConnections(rest)
            else -> println("Don't know how to list items with type [$itemType]")
        }
    }

    private fun listProjects() {
        val result = projectService.list()
        for (r in result) {
            println(r.name)
        }
    }

    private fun listConnections(args: List<String>) {
        if (args.size < EXTRA_PARAM_COUNT) {
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

        val connections = connectionService.getConnectionsFrom(fromConnectable, ConnectionType.TALKS_TO)

        connections.forEach { it ->
            println("Connection to [${it.getConnectionEndpointType()}, ${it.getConnectionEndpointType()}]")
        }
    }
}
