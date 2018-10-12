package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IDatastoreService
import org.ephyra.acropolis.service.api.ILoadBalancerService
import org.ephyra.acropolis.service.api.IQueueService
import org.ephyra.acropolis.service.api.IReverseProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

/**
 * Command for specializing an item.
 *
 * For example, system software can be specialized to represent a reverse proxy or load balancer etc.
 */
@ShellComponent
class SpecializeCommand {
    @Autowired
    private lateinit var datastoreService: IDatastoreService

    @Autowired
    private lateinit var reverseProxyService: IReverseProxyService

    @Autowired
    private lateinit var loadBalancerService: ILoadBalancerService

    @Autowired
    private lateinit var queueService: IQueueService

    /**
     * Handler for the specialize command.
     */
    @ShellMethod("Specialise a base software")
    fun specialize(baseId: Long, `as`: String, specializeType: String) {
        if (`as` != "as") {
            throw IllegalStateException("Invalid syntax, use 'specialize <baseId> as <specializeType>")
        }

        when (specializeType) {
            "datastore" -> datastoreService.create(baseId)
            "reverse-proxy" -> reverseProxyService.create(baseId)
            "load-balancer" -> loadBalancerService.create(baseId)
            "queue" -> queueService.create(baseId)
            else -> throw IllegalStateException("Unknown specialization")
        }
    }
}
