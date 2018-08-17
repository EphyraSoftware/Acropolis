package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareSpecializationEntity
import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.ephyra.acropolis.service.impl.ReverseProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class SpecializeCommand {
    @Autowired
    private lateinit var reverseProxyService: ReverseProxyService

    @ShellMethod("Specialise a base software")
    fun specialize(baseId: Long, to: String, specializeType: String) {
        if (to != "to") {
            throw IllegalStateException("Invalid syntax, use 'specialize <baseId> to <specializeType>")
        }

        when (specializeType) {
            "reverse-proxy" -> reverseProxyService.create(baseId)
            else -> throw IllegalStateException("Unknown specialization")
        }
    }
}
