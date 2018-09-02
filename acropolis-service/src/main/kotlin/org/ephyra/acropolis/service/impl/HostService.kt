package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.HostEntity
import org.ephyra.acropolis.persistence.api.persistence.HostPersistence
import org.ephyra.acropolis.service.api.IHostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HostService : IHostService {
    @Autowired
    private lateinit var persistence: HostPersistence

    override fun create(name: String, hostedBy: String?) {
        println("Acropolis service is creating host with name $name")
        val host = HostEntity()

        host.name = name
        if (hostedBy != null) {
            host.hostedBy = get(hostedBy)
        }
        persistence.create(host)
    }

    override fun delete(id: Long) {
        println("Acropolis service is deleting host #$id")
        persistence.delete(id)
    }

    override fun list(): List<HostEntity> {
        return persistence.getAll()
    }

    override fun get(name: String): HostEntity? {
        return persistence.find(name)
    }
}
