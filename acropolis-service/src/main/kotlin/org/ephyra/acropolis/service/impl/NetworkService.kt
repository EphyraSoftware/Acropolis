package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.api.INetworkService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Service for interactions and mutations around Networks
 * */
@Service
class NetworkService : INetworkService {
    val Logger = LoggerFactory.getLogger(NetworkService::class.java)

    @Autowired
    private lateinit var persistence: NetworkPersistence

    @Autowired
    private lateinit var projectService: IProjectService

    @Autowired
    private lateinit var computeInstanceService: IComputeInstanceService

    /**
     * Creates a new entity, to be associated with the given project name
     * @param projectName the name of the project to find and associate this entity with
     * @param name the name of the entity to create
     */
    override fun create(name: String, projectName: String) {
        val project = projectService.find(projectName)

        if (project == null) {
            Logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val network = NetworkEntity(name, project)
        persistence.create(network)
    }

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    override fun find(name: String, projectId: Long): NetworkEntity? {
        return persistence.findByName(name, projectId)
    }

    @Transactional
    override fun linkComputeInstance(networkId: Long, computeInstanceName: String, projectId: Long) {
        Logger.info("Linking compute isntance [$computeInstanceName]")

        val network = persistence.find(networkId, projectId)
                ?: throw IllegalStateException("Cannot link compute-instance to network because network with id [$networkId] was not found")

        val computeInstance = computeInstanceService.find(computeInstanceName, projectId)
                ?: throw IllegalStateException("Cannot link compute-instance to network because compute-instance with name [$computeInstanceName] was not found")

        network.computeInstanceList.add(computeInstance)
        persistence.update(network)

    }

}
