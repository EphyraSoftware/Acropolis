package org.ephyra.acropolis.shell;

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
    fun connect(fromType: String, fromId: String, to: String, toType: String, toId: String) {
        val source: IConnectable = connectHelper.typenameToConnectable(fromId, fromType)
        val target: IConnectable = connectHelper.typenameToConnectable(toId, toType)

        connectionService.create(source, target)
    }


}
