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
class SystemSoftwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""

    var description: String? = null

    @ManyToOne(optional=false)
    var project: ProjectEntity? = null
}