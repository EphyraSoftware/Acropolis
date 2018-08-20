package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
class NetworkEntity @JvmOverloads constructor (
    @Column(nullable = false)
    var name: String,

    @ManyToOne(optional = false)
    val project: ProjectEntity,

    @OneToMany
    val dataStoreList: List<DatastoreEntity> = ArrayList(),

    @OneToMany
    val systemSoftwareList: List<SystemSoftwareEntity> = ArrayList(),

    @OneToMany
    val applicationSoftwareList: List<ApplicationSoftwareEntity> = ArrayList(),

    @Column(nullable = true)
    var description: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long? = null
}
