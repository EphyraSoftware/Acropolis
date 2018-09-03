package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.INetworkService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class LinkCommand {
    private val Logger = LoggerFactory.getLogger(LinkCommand::class.java)

    @Autowired
    private lateinit var appState: AppState

    @Autowired
    private lateinit var networkService: INetworkService

    @ShellMethod("Link two items")
    fun link(fromType: String, fromName: String, to: String, toType: String, toName: String) {
        Logger.info("Linking [$fromType] with name [$fromName] to [$toType] with name [$toName]")

        val projectId = appState.currentProject?.id
        if (projectId == null) {
            Logger.error("No project selected. Use `select project <project name>`")
            return
        }

        when (toType) {
            "network" -> linkToNetwork(fromType, fromName, toName, projectId.toLong())
            else -> Logger.error("Cannot link to unknown type [$toType]")
        }
    }

    private fun linkToNetwork(fromType: String, fromName: String, toName: String, projectId: Long) {
        when (fromType) {
            "datastore" -> linkDatastoreToNetwork(fromName, toName, projectId)
            "application-software" -> linkApplicationSoftwareToNetwork(fromName, toName, projectId)
            "system-software" -> linkSystemSoftwareToNetwork(fromName, toName, projectId)
            else -> Logger.error("Cannot link unknown type [$fromType] to network")
        }
    }

    private fun linkSystemSoftwareToNetwork(fromName: String, toName: String, projectId: Long) {
        val network = networkService.get(toName, projectId)

        val networkId = network?.id
        if (networkId != null) {
            networkService.linkSystemSoftware(networkId, fromName, projectId)
        }
        else {
            Logger.error("No network found with name [$toName]")
        }
    }

    private fun linkApplicationSoftwareToNetwork(fromName: String, toName: String, projectId: Long) {
        val network = networkService.get(toName, projectId)

        val networkId = network?.id
        if (networkId != null) {
            networkService.linkApplicationSoftware(networkId, fromName, projectId)
        }
        else {
            Logger.error("No network found with name [$toName]")
        }
    }

    private fun linkDatastoreToNetwork(fromName: String, toName: String, projectId: Long) {
        val network = networkService.get(toName, projectId)

        val networkId = network?.id
        if (networkId != null) {
            networkService.linkDatastore(networkId, fromName, projectId)
        }
        else {
            Logger.error("No network found with name [$toName]")
        }
    }
}