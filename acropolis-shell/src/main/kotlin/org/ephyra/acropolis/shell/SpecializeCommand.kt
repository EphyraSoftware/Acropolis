package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareSpecializationEntity
import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class SpecializeCommand {
    @Autowired
    private lateinit var systemSoftwareService: ISystemSoftwareService

    @ShellMethod("Specialise a base software")
    fun specialize(baseType: String, baseId: Long, specializeType: String) {
        val specialization: SystemSoftwareSpecializationEntity = when (specializeType) {
            "reverse-proxy" -> ReverseProxyEntity()
            else -> throw IllegalStateException("Unknown specialisation")
        }

        when (baseType) {
            "system-software" -> systemSoftwareService.specialize(baseId, specialization)
            else -> throw IllegalStateException("Cannot specialise unknown base type [$baseType]")
        }
    }
}
