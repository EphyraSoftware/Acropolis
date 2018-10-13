package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.ConnectionType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


/**
 *
 * @property id Auto-generated database ID
 *
 * @property fromId The database ID of the left-hand type
 * @property fromEndpointType The ConnectionEndpointType enum value that represents the left-hand type
 *
 * @property toId The database ID of the right-hand type
 * @property toEndpointType The ConnectionEndpointType enum value that represents the right-hand type
 *
 * @property connectionType Enum ConnectionType value that represents what type of connection this is,
 * i.e. HostedBy, TalksTo
 * @property description A short, optional, string to describe this connection
 */
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
