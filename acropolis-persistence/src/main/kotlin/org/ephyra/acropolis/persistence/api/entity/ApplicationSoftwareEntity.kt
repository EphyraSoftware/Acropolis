package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

/**
 * Entity to model application software.
 *
 * Definition of application software
 *  Application software is computer software designed to perform a group of coordinated functions, tasks, or
 *  activities for the benefit of a user.
 *
 * Examples of application software
 *  - A web browser
 *  - A word processor
 *  - An interactive shell or terminal
 *
 *  @property id Auto-generated database ID
 *  @property name The name of the entity
 *  @property project The project to which this entity belongs, ManyToOne
 *  @property description A short, optional, string to describe this entity in more detail
 */
@Entity
data class ApplicationSoftwareEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        @ManyToOne(optional = false)
        val project: ProjectEntity,

        @Column(nullable = true)
        var description: String? = null
) : IConnectable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    /**
     * @return The ID of this model in a connection context
     */
    override fun getConnectionId(): Long {
        return id ?: -1
    }

    /**
     * @return The Enumerated ConnectionEndpointType value that corresponds to this type of entity
     */
    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.APPLICATION_SOFTWARE.type
    }
}
