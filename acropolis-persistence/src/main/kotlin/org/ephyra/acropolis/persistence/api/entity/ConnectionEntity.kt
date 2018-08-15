package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
data class ConnectionEntity @JvmOverloads constructor (
    @Column(nullable=false)
    val fromId: Long,

    @Column(nullable=false)
    val fromType: Int,

    @Column(nullable=false)
    val toId: Long,

    @Column(nullable=false)
    val toType: Int,

    @Column(nullable=true)
    var description: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}
