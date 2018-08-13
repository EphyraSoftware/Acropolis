package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

/**
 * Entity to model system software.
 *
 * Definition of system software
 *  System software is computer software designed to provide a platform to other software.
 *
 * Examples of system software
 *  - An operating system
 *  - A game engine
 *  - An automated industrial control system
 */
@Entity
data class SystemSoftwareEntity @JvmOverloads constructor (
    @Column(nullable=false)
    var name: String,

    @ManyToOne(optional=false)
    val project: ProjectEntity,

    @Column(nullable=true)
    var description: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
