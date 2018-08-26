package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.*
import org.springframework.stereotype.Service

@Service
class ConnectHelper {
    fun typenameToConnectable(id: String, type: String): IConnectable {
        return when (type) {
            "system-software" -> {
                val systemSoftwareEntity = SystemSoftwareEntity("", ProjectEntity())
                systemSoftwareEntity.id = id.toLong()
                systemSoftwareEntity
            }
            "application-software" -> {
                val applicationSoftwareEntity = ApplicationSoftwareEntity("", ProjectEntity(), HostEntity())
                applicationSoftwareEntity.id = id.toLong()
                applicationSoftwareEntity
            }
            "datastore" -> {
                val datastoreEntity = DatastoreEntity("", ProjectEntity())
                datastoreEntity.id = id.toLong()
                datastoreEntity
            }
            else -> throw IllegalStateException("Cannot connect from type [$type]")
        }
    }
}