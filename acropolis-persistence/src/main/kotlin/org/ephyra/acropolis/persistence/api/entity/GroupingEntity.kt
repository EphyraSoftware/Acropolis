package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

/**
 * @property id auto-generated database ID
 * @property datastoreList the Datastore entities that are grouped under this grouping entity
 * @property systemSoftwareList the systemSoftware entities that are grouped under this grouping entity
 * @property applicationSoftwareList the applicationSoftware entities that are grouped under this grouping entity
 *
 * */
@Entity
class GroupingEntity @JvmOverloads constructor(
        @ManyToMany
        var datastoreList: MutableList<DatastoreEntity> = ArrayList(),

        @ManyToMany
        val systemSoftwareList: MutableList<SystemSoftwareEntity> = ArrayList(),

        @ManyToMany
        val applicationSoftwareList: MutableList<ApplicationSoftwareEntity> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long? = null
}
