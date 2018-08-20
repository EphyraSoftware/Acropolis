package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.INetworkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NetworkService : INetworkService {
    @Autowired
    private lateinit var persistence: NetworkPersistence

    @Autowired
    private lateinit var projectService: ProjectService

    override fun create(name: String, projectName: String) {
        val project = projectService.getProject(projectName)

        val network = NetworkEntity(name, project)
        persistence.create(network)
    }
}
