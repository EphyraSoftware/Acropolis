package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.impl.ApplicationSoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationSoftwarePersistence {
    @Autowired
    private lateinit var repo: ApplicationSoftwareRepository

    fun create(application: ApplicationSoftwareEntity) {
        repo.save(application)
    }

    fun find(id: Long): ApplicationSoftwareEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    fun findByName(name: String, projectId: Long): ApplicationSoftwareEntity? {
        val entity = repo.findByNameAndProjectId(name, projectId.toInt())
        return if (entity.isPresent) entity.get() else null
    }
}
