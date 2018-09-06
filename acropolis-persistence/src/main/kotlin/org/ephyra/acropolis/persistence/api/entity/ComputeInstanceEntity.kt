package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

/**
 * Entity to model any such instance that is capable of running Application Software
 * i.e. Docker Container, Heroku Dyno, EC2 Instance, Local Machine, Server
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

    override fun getConnectionId(): Long {
        return id ?: -1
    }

    override fun getConnectionEndpointType(): Int {
        return ConnectionEndpointType.COMPUTE_INSTANCE.type
    }
}
