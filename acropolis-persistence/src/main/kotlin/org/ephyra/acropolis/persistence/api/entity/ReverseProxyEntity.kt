package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import javax.persistence.Entity

@Entity
class ReverseProxyEntity @JvmOverloads constructor(
        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {
    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.REVERSE_PROXY.type
    }
}
