package org.ephyra.acropolis.shell;

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.service.api.IConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class ConnectCommand {
    @Autowired
    private lateinit var connectionService: IConnectionService

    @Autowired
    private lateinit var connectHelper: ConnectHelper

    @ShellMethod("Connect two items")
    fun connect(fromEndpointType: String, fromId: String, connectionType: String, toEndpointType: String, toId: String) {
        val source: IConnectable = connectHelper.typenameToConnectable(fromId, fromEndpointType)
        val target: IConnectable = connectHelper.typenameToConnectable(toId, toEndpointType)
        when (connectionType) {
            "talks-to" -> connectionService.create(source, target, ConnectionType.TALKS_TO)
            "hosts" -> connectionService.create(source, target, ConnectionType.TALKS_TO)
            else -> println("Don't know how to create a [$connectionType] connection")
        }
    }


}
