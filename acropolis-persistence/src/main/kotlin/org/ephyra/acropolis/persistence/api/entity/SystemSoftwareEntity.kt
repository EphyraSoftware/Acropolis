package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

/**
 * Entity to model system software.
 *
 * Definition of system software
 *  System software is computer software designed to provide a platform to other software.
 *
 * Examples of system software
 *  - An operating system
 *  - A game engine
 *  - An automated industrial control system
 *
 *  @property id auto-generated database ID
 *  @property name The name of this entity
 *  @property project the project to which this entity belongs
 *  @property specialization polymorphic type field, auto-populated by Hibernate with the type of derived specialisations of SystemSoftwareEntity
 *  @property description A short description
 */
@Entity
data class SystemSoftwareEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        @ManyToOne(optional = false)
        val project: ProjectEntity,

        @OneToOne(optional = true)
        var specialization: SystemSoftwareSpecializationEntity? = null,

        @Column(nullable = true)
        var description: String? = null
) : IConnectable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    /**
     * @return The ID of this model in a connection context
     * */
    override fun getConnectionId(): Long {
        return id ?: -1
    }

    /**
     * @return The Enumerated ConnectionEndpointType value that corresponds to this type of entity
     * */
    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.SYSTEM_SOFTWARE.type
    }
}
