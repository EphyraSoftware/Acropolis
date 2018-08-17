package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
open class SystemSoftwareSpecializationEntity constructor(
    @OneToOne
    private val baseSoftware: SystemSoftwareEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private val id: Long? = null
}
