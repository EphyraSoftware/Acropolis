package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.ConnectionType
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

    override fun getConnectionId(): Long {
        return id ?: -1
    }

    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.SYSTEM_SOFTWARE.type
    }
}
