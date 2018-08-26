package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class HostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""

    @ManyToOne(optional = true)
    var hostedBy: HostEntity? = null
}
