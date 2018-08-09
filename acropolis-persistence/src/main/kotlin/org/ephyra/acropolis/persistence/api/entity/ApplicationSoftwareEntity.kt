package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

/**
 * Entity to model application software.
 *
 * Definition of application software
 *  Application software is computer software designed to perform a group of coordinated functions, tasks, or activities for the benefit of a user.
 *
 * Examples of application software
 *  - A web browser
 *  - A word processor
 *  - An interactive shell or terminal
 */
@Entity
class ApplicationSoftwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""

    var description: String? = null

    @ManyToOne(optional=false)
    var project: ProjectEntity? = null
}
