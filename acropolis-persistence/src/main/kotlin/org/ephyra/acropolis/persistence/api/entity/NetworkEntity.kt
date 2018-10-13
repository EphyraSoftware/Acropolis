package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

/**
 * Representation of a network for persistence.
 *
 * @property id Auto-generated ID database
 * @property name The name of the entity
 * @property project The project to which this entity belongs, ManyToOne
 * @property description A short, optional, string to describe this entity in more detail
 * @property computeInstanceList The list of compute instances included in this network
 */
@Entity
class NetworkEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        @ManyToOne(optional = false)
        val project: ProjectEntity,

        @Column(nullable = true)
        var description: String? = null,

        @OneToMany
        var computeInstanceList: MutableList<ComputeInstanceEntity> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null
}
