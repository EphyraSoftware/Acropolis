package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.springframework.data.repository.CrudRepository

internal interface ApplicationSoftwareRepository : CrudRepository<ApplicationSoftwareEntity, Long>
