package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.api.persistence.GroupingPersistence
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IDatastoreService
import org.ephyra.acropolis.service.api.INetworkService
import org.ephyra.acropolis.service.api.IProjectService
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

    override fun create(name: String, projectName: String) {
        val project = projectService.getProject(projectName)

        val network = NetworkEntity(name, project)
        persistence.create(network)
    }

    override fun get(name: String, projectId: Long): NetworkEntity? {
        return persistence.findByName(name, projectId)
    }

    @Transactional
    override fun linkDatastore(networkId: Long, datastoreName: String, projectId: Long) {
        Logger.info("Linking datastore [$datastoreName]")

        val network = persistence.getNetwork(networkId, projectId)
            ?: throw IllegalStateException("Cannot link datastore to network because network with id [$networkId] was not found")

        // TODO lookup with project id
        val datastore = datastoreService.get(datastoreName)
            ?: throw IllegalStateException("Cannot link datastore to network because datastore with name [$datastoreName] was not found")

        val grouping = network.groupingEntity
        if (grouping == null) {
            Logger.trace("There is no grouping, creating one")
            val newGrouping = GroupingEntity(mutableListOf(datastore))
            groupingPersistence.create(newGrouping)
            network.groupingEntity = newGrouping
            persistence.updateGrouping(network)
        }
        else {
            Logger.trace("Updating grouping to include datastore")
            grouping.datastoreList.add(datastore)
            groupingPersistence.updateDatastoreList(grouping)
        }
    }

    @Transactional
    override fun linkApplicationSoftware(networkId: Long, applicationSoftwareName: String, projectId: Long) {
        Logger.info("Linking application software [$applicationSoftwareName]")

        val network = persistence.getNetwork(networkId, projectId)
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
        }
        else {
            Logger.trace("Updating grouping to include application-software [$applicationSoftware.name]")
            grouping.applicationSoftwareList.add(applicationSoftware)
            groupingPersistence.updateApplicationSoftwareList(grouping)
        }
    }

    override fun linkSystemSoftware(networkId: Long, systemSoftwareId: Long, projectId: Long) {

    }
}
