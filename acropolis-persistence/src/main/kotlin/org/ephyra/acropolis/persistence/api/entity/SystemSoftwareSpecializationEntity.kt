package org.ephyra.acropolis.persistence.api.entity

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class SystemSoftwareSpecializationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private val id: Long? = null
}
