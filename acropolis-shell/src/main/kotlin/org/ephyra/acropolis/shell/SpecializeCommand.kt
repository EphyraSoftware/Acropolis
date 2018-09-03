package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.ILoadBalancerService
import org.ephyra.acropolis.service.api.IQueueService
import org.ephyra.acropolis.service.impl.ReverseProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class SpecializeCommand {
    @Autowired
    private lateinit var reverseProxyService: ReverseProxyService

    @Autowired
    private lateinit var loadBalancerService: ILoadBalancerService

    @Autowired
    private lateinit var queueService: IQueueService

    @ShellMethod("Specialise a base software")
    fun specialize(baseId: Long, `as`: String, specializeType: String) {
        if (`as` != "as") {
            throw IllegalStateException("Invalid syntax, use 'specialize <baseId> as <specializeType>")
        }

        when (specializeType) {
            "reverse-proxy" -> reverseProxyService.create(baseId)
            "load-balancer" -> loadBalancerService.create(baseId)
            "queue" -> queueService.create(baseId)
            else -> throw IllegalStateException("Unknown specialization")
        }
    }
}
