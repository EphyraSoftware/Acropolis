package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.persistence.impl.SystemSoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SystemSoftwarePersistence {
    @Autowired
    private lateinit var repo: SystemSoftwareRepository

    fun create(systemSoftware: SystemSoftwareEntity) {
        repo.save(systemSoftware)
    }
}