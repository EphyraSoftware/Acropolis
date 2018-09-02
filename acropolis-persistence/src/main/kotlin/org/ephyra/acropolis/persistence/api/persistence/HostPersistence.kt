package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.HostEntity
import org.ephyra.acropolis.persistence.impl.HostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HostPersistence {
    @Autowired
    private lateinit var repo: HostRepository

    fun create(host: HostEntity) {
        repo.save(host)
    }

    fun delete(id: Long) {
        repo.deleteById(id)
    }

    fun getAll(): List<HostEntity> {
        return repo.findAll().toList()
    }

    fun find(name: String): HostEntity? {
        val entity = repo.findByName(name)
        return if (entity.isPresent) entity.get() else null
    }

}
