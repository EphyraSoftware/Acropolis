package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.entity.LoadBalancerEntity
import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.ephyra.acropolis.service.api.INetworkService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.stereotype.Component
import javax.transaction.Transactional

/**
 * Command for finding items.
 */
@ShellComponent
class FindCommand {
    private val Logger = LoggerFactory.getLogger(FindCommand::class.java)

    @Autowired
    private lateinit var appState: AppState

    @Autowired
    private lateinit var systemSoftwareService: ISystemSoftwareService

    @Autowired
    private lateinit var networkFinder: NetworkFinder

    /**
     * Handler for the find command.
     */
    @ShellMethod("Query an entity")
    fun find(itemType: String, itemIdentifier: String) {
        val proj = appState.currentProject
        if (proj == null) {
            Logger.error("No project selected")
            return
        }

        when (itemType) {
            "system-software" -> findSystemSoftware(proj.id, itemIdentifier)
            "network" -> networkFinder.findNetwork(proj.id, itemIdentifier)
            else -> Logger.warn("Don't know how to look for item type [$itemType]")
        }
    }

    private fun findSystemSoftware(projectId: Long, name: String) {
        val systemSoftware = systemSoftwareService.find(name, projectId)
        if (systemSoftware == null) {
            println("Not found")
        } else {
            println("Found system-software with name $name.")
            if (systemSoftware.description != null) println("Description is ${systemSoftware.description}")
            val specialization = systemSoftware.specialization
            when (specialization) {
                is ReverseProxyEntity -> {
                    println("The software is specialised as: reverse-proxy")
                    if (specialization.description != null) println("  with description ${specialization.description}")
                }
                is LoadBalancerEntity -> {
                    println("The software is specialised as: load-balancer")
                    if (specialization.description != null) println("  with description ${specialization.description}")
                }
                else -> {
                    if (specialization == null) {
                        println("The software is not specialized")
                    } else {
                        val connectionType = specialization.getConnectionEndpointType()
                        println("unknown specialization $connectionType")
                    }
                }

            }
        }
    }
}

// Look how much fun is needed to make the transaction work with annotations.
// 1. Needs to be depth 1 Spring proxy access
// 2. Must be on a public method
// Thanks to http://blog.timmattison.com/archives/2012/04/19/tips-for-debugging-springs-transactional-annotation/
/**
 * Helper to looking up networks.
 */
@Component
class NetworkFinder {
    private val Logger = LoggerFactory.getLogger(NetworkFinder::class.java)

    @Autowired
    private lateinit var networkService: INetworkService

    /**
     * Transactional lookup for a network.
     */
    @Transactional
    fun findNetwork(projectId: Long, itemIdentifier: String) {
        Logger.trace("Looking for network with name [$itemIdentifier] in project with id [$projectId]")

        val network = networkService.find(itemIdentifier, projectId)
        if (network == null) {
            Logger.info("No network was found with name $itemIdentifier")
        } else {
            Logger.info("Found network with id [${network.id}]")

            Logger.info("Name: [${network.name}]")
            Logger.info("Description: [${network.description}]")
        }
    }
}
