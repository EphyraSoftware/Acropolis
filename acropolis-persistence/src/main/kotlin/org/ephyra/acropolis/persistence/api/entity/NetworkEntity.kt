package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

/**
 *  @property id Auto-generated ID database
 *  @property name The name of the entity
 *  @property project The project to which this entity belongs, ManyToOne
 *  @property groupingEntity The group which contains entities that belong to this network
 *  @property description A short, optional, string to describe this entity in more detail
 */
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
