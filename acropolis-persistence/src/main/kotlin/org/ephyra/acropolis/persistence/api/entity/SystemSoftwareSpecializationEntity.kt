package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

/**
 * Base class for building specializations of system-software
 */
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
