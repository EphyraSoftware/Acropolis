package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class GroupingEntity @JvmOverloads constructor (
    @OneToMany
    val dataStoreList: MutableList<DatastoreEntity> = ArrayList(),

    @OneToMany
    val systemSoftwareList: MutableList<SystemSoftwareEntity> = ArrayList(),

    @OneToMany
    val applicationSoftwareList: MutableList<ApplicationSoftwareEntity> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long? = null
}
