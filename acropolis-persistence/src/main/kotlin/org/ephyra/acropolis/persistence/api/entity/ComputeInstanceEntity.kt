package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

/**
 * Entity to model any such instance that is capable of running Application Software
 * i.e. Docker Container, Heroku Dyno, EC2 Instance, Local Machine, Server
 *
 *  @property id Auto-generated database ID
 *  @property name The name of the entity
 *  @property project The project to which this entity belongs, ManyToOne
 *  @property description A short, optional, string to describe this entity in more detail
 */
@Entity
data class ComputeInstanceEntity @JvmOverloads constructor(
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
        return ConnectionEndpointType.COMPUTE_INSTANCE.type
    }
}
