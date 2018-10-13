package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.api.INetworkService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Service for interactions and mutations around Networks
 */
@Service
class NetworkService : INetworkService {
    private val logger = LoggerFactory.getLogger(NetworkService::class.java)

    @Autowired
    private lateinit var persistence: NetworkPersistence

    @Autowired
    private lateinit var projectService: IProjectService

    @Autowired
    private lateinit var computeInstanceService: IComputeInstanceService

    override fun create(name: String, projectName: String) {
        val project = projectService.find(projectName)

        if (project == null) {
            logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val network = NetworkEntity(name, project)
        persistence.create(network)
    }

    override fun find(name: String, projectId: Long): NetworkEntity? {
        return persistence.findByName(name, projectId)
    }

    @Transactional
    override fun linkComputeInstance(networkId: Long, computeInstanceName: String, projectId: Long) {
        logger.info("Linking compute isntance [$computeInstanceName]")

        val network = persistence.find(networkId, projectId)
                ?: throw IllegalStateException("Cannot link compute-instance to network because network with " +
                        "id [$networkId] was not found")

        val computeInstance = computeInstanceService.find(computeInstanceName, projectId)
                ?: throw IllegalStateException("Cannot link compute-instance to network because compute-instance " +
                        "with name [$computeInstanceName] was not found")

        network.computeInstanceList.add(computeInstance)
        persistence.update(network)

    }

}
