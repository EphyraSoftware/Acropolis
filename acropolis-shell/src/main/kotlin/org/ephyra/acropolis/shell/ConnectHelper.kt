package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.springframework.stereotype.Service

/**
 * Helper for working with connections between entities.
 */
@Service
class ConnectHelper {
    /**
     * Factory method for constructing a connectible entity from its type name.
     *
     * @param id The id to assign to the constructed entity
     * @param type The type name of the entity to construct
     */
    fun typenameToConnectable(id: String, type: String): IConnectable {
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
            else -> throw IllegalStateException("Cannot connect from type [$type]")
        }
    }
}