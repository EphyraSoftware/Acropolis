package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import javax.persistence.*

@Entity
data class ConnectionEntity @JvmOverloads constructor(
        @Column(nullable = false)
        val fromId: Long,

        @Column(nullable = false)
        val fromEndpointType: Int,

        @Column(nullable = false)
        val toId: Long,

        @Column(nullable = false)
        val toEndpointType: Int,

        @Column(nullable = false)
        val connectionType: Int = ConnectionType.TALKS_TO.type,

        @Column(nullable = true)
        var description: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}
