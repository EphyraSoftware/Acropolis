package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class LoadBalancerEntity @JvmOverloads constructor (
        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {
    override fun getConnectionType(): Int {
        return ConnectionType.LOAD_BALANCER.type
    }
}
