package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
open class SystemSoftwareSpecializationEntity constructor (
    @Column(nullable=true)
    var description: String? = null
) : IConnectable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected val id: Long? = null

    override fun getConnectionId(): Long {
        return id ?: -1
    }

    override fun getConnectionType(): Int {
        return ConnectionType.REVERSE_PROXY.type
    }
}
