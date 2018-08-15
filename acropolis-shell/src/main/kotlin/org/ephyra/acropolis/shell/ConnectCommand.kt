package org.ephyra.acropolis.shell;

import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
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
        val source: IConnectable = typenameToConnectable(fromId, fromType)
        val target : IConnectable = typenameToConnectable(toId, toType)

        connectionService.create(source, target)
    }

    fun typenameToConnectable(id: String, type: String): IConnectable
    {
        return when (type) {
            "system-software" -> {
                val systemSoftwareEntity = SystemSoftwareEntity("", ProjectEntity())
                systemSoftwareEntity.id = id.toLong()
                systemSoftwareEntity
            }
            "application-software" -> {
                val applicationSoftwareEntity = ApplicationSoftwareEntity("", ProjectEntity())
                applicationSoftwareEntity.id = id.toLong()
                applicationSoftwareEntity
            }
            else -> throw IllegalStateException("Cannot connect from type [$id]")
        }
    }
}
