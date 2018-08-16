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

    fun find(id: Long): SystemSoftwareEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    fun updateSpecialisation(systemSoftware: SystemSoftwareEntity) {
        repo.save(systemSoftware)
    }
}
