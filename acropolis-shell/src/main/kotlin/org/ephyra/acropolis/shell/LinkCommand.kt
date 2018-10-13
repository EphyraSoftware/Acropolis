package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.api.INetworkService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

/**
 * Command for linking items together.
 */
@ShellComponent
class LinkCommand {
    private val logger = LoggerFactory.getLogger(LinkCommand::class.java)

    @Autowired
    private lateinit var appState: AppState

    @Autowired
    private lateinit var computeInstanceService: IComputeInstanceService

    @Autowired
    private lateinit var networkService: INetworkService

    /**
     * Handler for the link command.
     */
    @ShellMethod("Link two items")
    fun link(fromType: String, fromName: String, to: String, toType: String, toName: String) {
        logger.info("Linking [$fromType] with name [$fromName] to [$toType] with name [$toName]")

        val projectId = appState.currentProject?.id
        if (projectId == null) {
            logger.error("No project selected. Use `select project <project name>`")
            return
        }

        when (toType) {
            "compute-instance" -> linkToComputeInstance(fromType, fromName, toName, projectId.toLong())
            "network" -> linkToNetwork(fromType, fromName, toName, projectId.toLong())
            else -> logger.error("Cannot link to unknown type [$toType]")
        }
    }

    private fun linkToNetwork(fromType: String, fromName: String, toName: String, projectId: Long) {
        when (fromType) {
            "compute-instance" -> linkComputeInstanceToNetwork(fromName, toName, projectId)
            else -> logger.error("Cannot link unknown type [$fromType] to network")
        }
    }

    private fun linkToComputeInstance(fromType: String, fromName: String, toName: String, projectId: Long) {
        when (fromType) {
            "application-software" -> linkApplicationSoftwareToComputeInstance(fromName, toName, projectId)
            "system-software" -> linkSystemSoftwareToComputeInstance(fromName, toName, projectId)
            else -> logger.error("Cannot link unknown type [$fromType] to compute-instance")
        }
    }

    private fun linkComputeInstanceToNetwork(fromName: String, toName: String, projectId: Long) {
        val network = networkService.find(toName, projectId)

        val networkId = network?.id
        if (networkId != null) {
            networkService.linkComputeInstance(networkId, fromName, projectId)
        } else {
            logger.error("No network found with name [$toName]")
        }
    }

    private fun linkSystemSoftwareToComputeInstance(fromName: String, toName: String, projectId: Long) {
        val computeInstance = computeInstanceService.find(toName, projectId)

        val computeInstanceId = computeInstance?.id
        if (computeInstanceId != null) {
            computeInstanceService.linkSystemSoftware(computeInstanceId, fromName, projectId)
        } else {
            logger.error("No compute-instance found with name [$toName]")
        }
    }

    private fun linkApplicationSoftwareToComputeInstance(fromName: String, toName: String, projectId: Long) {
        val computeInstance = networkService.find(toName, projectId)

        val computeInstanceId = computeInstance?.id
        if (computeInstanceId != null) {
            computeInstanceService.linkApplicationSoftware(computeInstanceId, fromName, projectId)
        } else {
            logger.error("No compute-instance found with name [$toName]")
        }
    }
}
