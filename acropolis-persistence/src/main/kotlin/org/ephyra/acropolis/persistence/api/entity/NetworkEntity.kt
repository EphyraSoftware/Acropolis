package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class NetworkEntity @JvmOverloads constructor (
    @Column(nullable = false)
    var name: String,

    @ManyToOne(optional = false)
    val project: ProjectEntity,

    @Column(nullable = true)
    var description: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long? = null
}
