package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

@Entity
data class ReverseProxyEntity @JvmOverloads constructor (
    @Transient
    var systemSoftwareEntity: SystemSoftwareEntity,

    @Column(nullable=true)
    var description: String? = null
) : SystemSoftwareSpecializationEntity(systemSoftwareEntity), IConnectable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    override fun getConnectionId(): Long {
        return id ?: -1
    }

    override fun getConnectionType(): Int {
        return ConnectionType.REVERSE_PROXY.type
    }
}
