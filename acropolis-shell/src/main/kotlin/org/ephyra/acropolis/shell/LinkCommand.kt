package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IDatastoreService
import org.ephyra.acropolis.service.api.INetworkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class LinkCommand {
    @Autowired
    private lateinit var networkService: INetworkService

    @ShellMethod("Link two items")
    fun link(fromType: String, fromName: String, to: String, toType: String, toName: String) {
        when (toType) {
            "network" -> linkToNetwork(fromType, fromName, toName)
        }
    }

    private fun linkToNetwork(fromType: String, fromName: String, toName: String) {
        when (fromType) {
            "datastore" -> linkDatastoreToNetwork(fromName, toName)
        }
    }

    private fun linkDatastoreToNetwork(fromName: String, toName: String) {
        val network = networkService.get(toName)

        val networkId = network?.id
        if (networkId != null) {
            networkService.linkDatastore(networkId, fromName)
        }
    }
}