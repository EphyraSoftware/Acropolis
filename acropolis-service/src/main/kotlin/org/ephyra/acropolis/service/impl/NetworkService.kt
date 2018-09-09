package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.api.persistence.GroupingPersistence
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class NetworkService : INetworkService {
    val Logger = LoggerFactory.getLogger(INetworkService::class.java)

    @Autowired
    private lateinit var persistence: NetworkPersistence

    @Autowired
    private lateinit var groupingPersistence: GroupingPersistence

    @Autowired
    private lateinit var projectService: IProjectService

    @Autowired
    private lateinit var datastoreService: IDatastoreService

    @Autowired
    private lateinit var applicationSoftwareService: IApplicationSoftwareService

    @Autowired
    private lateinit var systemSoftwareService: ISystemSoftwareService

    /**
     * Creates a new entity, to be associated with the given project name
     * @param projectName the name of the project to find and associate this entity with
     * @param name the name of the entity to create
     */
    override fun create(name: String, projectName: String) {
        val project = projectService.get(projectName)

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
    override fun get(name: String, projectId: Long): NetworkEntity? {
        return persistence.findByName(name, projectId)
    }

    @Transactional
    override fun linkDatastore(networkId: Long, datastoreName: String, projectId: Long) {
        Logger.info("Linking datastore [$datastoreName]")

        val network = persistence.find(networkId, projectId)
                ?: throw IllegalStateException("Cannot link datastore to network because network with id [$networkId] was not found")

        // TODO lookup with project id
        val datastore = datastoreService.get(datastoreName, projectId)
                ?: throw IllegalStateException("Cannot link datastore to network because datastore with name [$datastoreName] was not found")

        val grouping = network.groupingEntity
        if (grouping == null) {
            Logger.trace("There is no grouping, creating one")
            val newGrouping = GroupingEntity(mutableListOf(datastore))
            groupingPersistence.create(newGrouping)
            network.groupingEntity = newGrouping
            persistence.updateGrouping(network)
        } else {
            Logger.trace("Updating grouping to include datastore")
            grouping.datastoreList.add(datastore)
            groupingPersistence.update(grouping)
        }
    }

    @Transactional
    override fun linkApplicationSoftware(networkId: Long, applicationSoftwareName: String, projectId: Long) {
        Logger.info("Linking application software [$applicationSoftwareName]")

        val network = persistence.find(networkId, projectId)
                ?: throw IllegalStateException("Cannot link application-software to network because network with id [$networkId] was not found")

        val applicationSoftware = applicationSoftwareService.find(applicationSoftwareName, projectId)
                ?: throw IllegalStateException("Cannot link application-software to network because application-software with name [$applicationSoftwareName] was not found")

        val grouping = network.groupingEntity
        if (grouping == null) {
            Logger.trace("There is no grouping, creating one")
            val newGrouping = GroupingEntity(mutableListOf(), mutableListOf(), mutableListOf(applicationSoftware))
            groupingPersistence.create(newGrouping)
            network.groupingEntity = newGrouping
            persistence.updateGrouping(network)
        } else {
            Logger.trace("Updating grouping to include application-software [$applicationSoftware.name]")
            grouping.applicationSoftwareList.add(applicationSoftware)
            groupingPersistence.update(grouping)
        }
    }

    @Transactional
    override fun linkSystemSoftware(networkId: Long, systemSoftwareName: String, projectId: Long) {
        Logger.info("Linking system-software [$systemSoftwareName]")

        val network = persistence.find(networkId, projectId)
                ?: throw IllegalStateException("Cannot link system-software to network because network with id [$networkId] was not found")

        val systemSoftware = systemSoftwareService.get(systemSoftwareName, projectId)
                ?: throw IllegalStateException("Cannot link system-software to network because system-software with name [$systemSoftwareName] was not found")

        val grouping = network.groupingEntity
        if (grouping == null) {
            Logger.trace("There is no grouping, creating one")
            val newGrouping = GroupingEntity(mutableListOf(), mutableListOf(systemSoftware), mutableListOf())
            groupingPersistence.create(newGrouping)
            network.groupingEntity = newGrouping
            persistence.updateGrouping(network)
        } else {
            Logger.trace("Updating grouping to include system-software [$systemSoftware.name]")
            grouping.systemSoftwareList.add(systemSoftware)
            groupingPersistence.update(grouping)
        }
    }
}
