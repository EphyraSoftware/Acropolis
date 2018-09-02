package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import javax.persistence.Entity

@Entity
class LoadBalancerEntity @JvmOverloads constructor (
        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {
    override fun getConnectionType(): Int {
        return ConnectionType.LOAD_BALANCER.type
    }
}
