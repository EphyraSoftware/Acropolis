package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class NetworkEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        @ManyToOne(optional = false)
        val project: ProjectEntity,

        @OneToOne(optional = true)
        var groupingEntity: GroupingEntity? = null,

        @Column(nullable = true)
        var description: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null
}