package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class GroupingEntity @JvmOverloads constructor (
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
