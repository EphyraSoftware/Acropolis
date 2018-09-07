package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity
import org.ephyra.acropolis.persistence.impl.ComputeInstanceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ComputeInstancePersistence {
    @Autowired
    private lateinit var repo: ComputeInstanceRepository

    fun create(computeInstance: ComputeInstanceEntity) {
        repo.save(computeInstance)
    }

    fun find(id: Long): ComputeInstanceEntity? {
        val entity = repo.findById(id)
        return if (entity.isPresent) entity.get() else null
    }

    fun findByName(name: String, projectId: Long): ComputeInstanceEntity? {
        val entity = repo.findByNameAndProjectId(name, projectId)
        return if (entity.isPresent) entity.get() else null
    }
}