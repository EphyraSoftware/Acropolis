package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * The base type around which everything is centred. Acts as an entrypoint/parent scoping for all
 * subsequent types and operations.
 */
@Entity
class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var name: String = ""
}
