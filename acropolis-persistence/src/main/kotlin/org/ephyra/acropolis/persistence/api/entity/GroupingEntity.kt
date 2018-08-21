package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class GroupingEntity @JvmOverloads constructor (
    @OneToMany
    val dataStoreList: List<DatastoreEntity> = ArrayList(),

    @OneToMany
    val systemSoftwareList: List<SystemSoftwareEntity> = ArrayList(),

    @OneToMany
    val applicationSoftwareList: List<ApplicationSoftwareEntity> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long? = null
}
