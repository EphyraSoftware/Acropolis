package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

/**
 * Entity to model simple data stores. From local hard drives to remote buckets.
 */
@Entity
class DatastoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""

    var description: String? = null

    @ManyToOne(optional=false)
    var project: ProjectEntity? = null
}
