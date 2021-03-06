package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

/**
 * @property id auto-generated database ID
 * @property systemSoftwareList the systemSoftware entities that are grouped under this grouping entity
 * @property applicationSoftwareList the applicationSoftware entities that are grouped under this grouping entity
 *
 */
@Entity
class GroupingEntity @JvmOverloads constructor(
        @Column(nullable = false)
        var name: String,

        @ManyToOne(optional = false)
        val project: ProjectEntity,

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
