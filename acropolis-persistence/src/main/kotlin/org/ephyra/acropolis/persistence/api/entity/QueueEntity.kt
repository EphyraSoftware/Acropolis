package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class QueueEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {

    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.QUEUE.type
    }
}