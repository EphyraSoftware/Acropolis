package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class HostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var name: String = ""

    @ManyToOne(optional = true)
    var hostedBy: HostEntity? = null
}
