package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class SystemSoftwareSpecializationEntity constructor(
        @Column(nullable = true)
        var description: String? = null
) : IConnectable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected val id: Long? = null

    /**
     * @return The ID of this model in a connection context
     */
    override fun getConnectionId(): Long {
        return id ?: -1
    }

    /**
     * @return The Enumerated ConnectionEndpointType value that corresponds to this type of entity
     */
    abstract override fun getConnectionEndpointType(): Int
}
