package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import javax.persistence.*

/**
 * Entity to model simple data stores. From local hard drives to remote buckets.
 */
@Entity
data class DatastoreEntity @JvmOverloads constructor(
    @Column(nullable = false)
    var name: String,

    @ManyToOne(optional = false)
    val project: ProjectEntity,

    @ManyToOne(optional = false)
    val hostedBy: HostEntity,

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
        return ConnectionType.SYSTEM_SOFTWARE.type
    }
}

