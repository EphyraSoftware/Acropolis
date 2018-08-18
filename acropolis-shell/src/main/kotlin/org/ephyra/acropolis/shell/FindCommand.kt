package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class FindCommand {
    @Autowired
    private lateinit var systemSoftwareService: ISystemSoftwareService

    @ShellMethod("Query an entity")
    fun find(itemType: String, itemIdentifier: String) {
        when(itemType) {
            "system-software" -> findSystemSoftware(itemIdentifier)
        }
    }

    private fun findSystemSoftware(name: String) {
        val systemSoftware = systemSoftwareService.get(name)
        if (systemSoftware == null) {
            println("Not found")
        }
        else {
            println("Found system-software with name $name.")
            if (systemSoftware.description != null) println("Description is ${systemSoftware.description}")
            if (systemSoftware.specialization != null) {
                print("The software is specialised to: ")
                val specialization = systemSoftware.specialization
                when (specialization) {
                    is ReverseProxyEntity -> {
                        println("reverse-proxy")
                        if (specialization.description != null) println("  with description ${specialization.description}")
                    }
                }
            }
        }
    }
}
