package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import javax.persistence.Entity

/**
 * Entity to model simple data stores. From local hard drives to remote buckets.
 *
 * Specialisation of SystemSoftware
 *  @see SystemSoftwareEntity for params
 */
@Entity
class DatastoreEntity @JvmOverloads constructor(
        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {

    /**
     * @return The Enumerated ConnectionEndpointType value that corresponds to this type of entity
     */
    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.SYSTEM_SOFTWARE.type
    }
}

