package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Specialisation of SystemSoftware
 *  @see SystemSoftwareEntity for params
 */
@Entity
class QueueEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {

    /**
     * @return The Enumerated ConnectionEndpointType value that corresponds to this type of entity
     */
    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.QUEUE.type
    }
}