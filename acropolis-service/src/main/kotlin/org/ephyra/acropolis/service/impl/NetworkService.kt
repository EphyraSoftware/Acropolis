package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.api.persistence.GroupingPersistence
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.IDatastoreService
import org.ephyra.acropolis.service.api.INetworkService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    override fun create(name: String, projectName: String) {
        val project = projectService.getProject(projectName)

        val network = NetworkEntity(name, project)
        persistence.create(network)
    }

    override fun get(name: String): NetworkEntity? {
        return persistence.findByName(name)
    }

    override fun linkDatastore(networkId: Long, datastoreName: String) {
        Logger.info("Linking datastore [$datastoreName]")

        val network = persistence.getNetwork(networkId)
            ?: throw IllegalStateException("Cannot link datastore to network because network with id [$networkId] was not found")

        var grouping = network.groupingEntity
        if (grouping == null) {
            val newGrouping = GroupingEntity()
            groupingPersistence.create(newGrouping)
            network.groupingEntity = newGrouping
            grouping = newGrouping
        }

        val datastore = datastoreService.get(datastoreName)
            ?: throw IllegalStateException("Cannot link datastore to network because datastore with name [$datastoreName] was not found")

        val datastoreList = grouping.dataStoreList
        datastoreList.add(datastore)

        persistence.updateGrouping(network)
    }

    override fun linkApplicationSoftware(networkId: Long, applicationSoftwareId: Long) {

    }

    override fun linkSystemSoftware(networkId: Long, systemSoftwareId: Long) {

    }
}
