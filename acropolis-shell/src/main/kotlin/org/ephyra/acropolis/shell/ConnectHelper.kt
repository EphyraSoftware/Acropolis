package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
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
                val applicationSoftwareEntity = ApplicationSoftwareEntity("", ProjectEntity())
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