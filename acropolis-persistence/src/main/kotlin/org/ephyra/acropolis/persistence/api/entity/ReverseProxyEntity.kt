package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import javax.persistence.Entity

/**
 * Specialisation of SystemSoftware
 *  @see SystemSoftwareEntity for params
 */
@Entity
class ReverseProxyEntity @JvmOverloads constructor(
        desc: String? = null
) : SystemSoftwareSpecializationEntity(desc) {

    /**
     * @return The Enumerated ConnectionEndpointType value that corresponds to this type of entity
     */
    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.REVERSE_PROXY.type
    }
}
