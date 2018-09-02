package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

/**
 * Entity to model application software.
 *
 * Definition of application software
 *  Application software is computer software designed to perform a group of coordinated functions, tasks, or activities for the benefit of a user.
 *
 * Examples of application software
 *  - A web browser
 *  - A word processor
 *  - An interactive shell or terminal
 */
@Entity
data class ApplicationSoftwareEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        @ManyToOne(optional = false)
        val project: ProjectEntity,

        @ManyToOne(optional = true)
        val hostedBy: HostEntity?,

        @Column(nullable = true)
        var description: String? = null
) : IConnectable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    override fun getConnectionId(): Long {
        return id ?: -1
    }

    override fun getConnectionType(): Int {
        return ConnectionType.APPLICATION_SOFTWARE.type
    }
}
