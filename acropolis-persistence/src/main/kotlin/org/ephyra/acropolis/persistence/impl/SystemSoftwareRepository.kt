package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.springframework.data.repository.CrudRepository

internal interface SystemSoftwareRepository : CrudRepository<SystemSoftwareEntity, Long>
