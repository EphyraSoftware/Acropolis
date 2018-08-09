package org.ephyra.acropolis.persistence.api

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.impl.ApplicationSoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ApplicationSoftwarePersistence {
    @Autowired
    private lateinit var repo: ApplicationSoftwareRepository

    fun create(application: ApplicationSoftwareEntity) {
        repo.save(application)
    }
}