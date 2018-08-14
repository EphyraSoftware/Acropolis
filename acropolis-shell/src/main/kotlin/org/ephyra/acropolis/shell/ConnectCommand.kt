package org.ephyra.acropolis.shell;

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.service.api.IConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class ConnectCommand {
    @Autowired
    private lateinit var connectionService: IConnectionService

    @ShellMethod("Connect two items")
    fun connect(fromType: String, fromId: String, to: String, toType: String, toId: String) {
        val source = when (fromType) {
            "system-software" -> SystemSoftwareEntity::class
            "application-software" -> ApplicationSoftwareEntity::class
            else -> throw IllegalStateException("Cannot connect from type [$fromType]")
        }

        val target = when (toType) {
            "system-software" -> SystemSoftwareEntity::class
            "application-software" -> ApplicationSoftwareEntity::class
            else -> throw IllegalStateException("Cannot convert to type [$toType]")
        }

        connectionService.create(fromId.toLong(), source, toId.toLong(), target)
    }
}
