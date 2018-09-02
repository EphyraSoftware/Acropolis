package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import javax.persistence.Entity

@Entity
class ReverseProxyEntity @JvmOverloads constructor (
    desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {
    override fun getConnectionType(): Int {
        return ConnectionType.REVERSE_PROXY.type
    }
}
