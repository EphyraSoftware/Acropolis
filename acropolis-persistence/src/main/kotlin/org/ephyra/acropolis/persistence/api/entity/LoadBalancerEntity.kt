package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import javax.persistence.Entity

@Entity
class LoadBalancerEntity @JvmOverloads constructor(
        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {
    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.LOAD_BALANCER.type
    }
}
