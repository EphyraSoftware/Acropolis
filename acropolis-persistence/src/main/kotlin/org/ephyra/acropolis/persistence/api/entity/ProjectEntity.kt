package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Specialisation of SystemSoftware
 *  @see SystemSoftwareEntity for params
 */
@Entity
class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var name: String = ""
}
