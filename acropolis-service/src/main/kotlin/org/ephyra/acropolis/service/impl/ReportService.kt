package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.report.api.IReportRunner
import org.ephyra.acropolis.report.api.model.Graph
import org.ephyra.acropolis.report.api.model.GraphContainer
import org.ephyra.acropolis.report.api.model.Node
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IConnectionService
import org.ephyra.acropolis.service.api.IReportService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

/**
 * Report service implementation
 */
@Service
class ReportService : IReportService {
    private val logger: Logger = LoggerFactory.getLogger(ApplicationSoftwareService::class.java)

    @Autowired
    private lateinit var applicationService: IApplicationSoftwareService

    @Autowired
    private lateinit var systemService: ISystemSoftwareService

    @Autowired
    private lateinit var connectionService: IConnectionService

    @Autowired
    private lateinit var reportRunner: IReportRunner

    override fun runSoftwareReport(projectName: String) {
        logger.trace("Starting to run software report for project [$projectName]")

        val applications = applicationService.findAll(projectName)
        val systems = systemService.findAll(projectName)

        val nodeMap: MutableMap<IConnectable, Node> = HashMap()
        val graph = Graph()

        applications.forEach { app ->
            val node = Node(app.name)
            graph.addNode(node)

            nodeMap[app] = node
        }

        systems.forEach { system ->
            val node = Node(system.name)
            graph.addNode(node)

            nodeMap[system] = node
        }

        nodeMap.forEach { fromConnectable, fromNode ->
            val connections = connectionService.getConnectionsFrom(fromConnectable, ConnectionType.TALKS_TO)
            connections.forEach { toConnection ->
                val toNode = when (toConnection) {
                    is SystemSoftwareEntity -> graph.findNode(toConnection.name)
                    is ApplicationSoftwareEntity -> graph.findNode(toConnection.name)
                    else -> throw IllegalStateException("Unknown connection type type")
                } ?: throw IllegalStateException("Unable to find node to connect to")

                graph.addDirectedEdge(fromNode, toNode)
            }
        }

        val graphContainer = GraphContainer(graph)
        reportRunner.run(graphContainer)
    }
}
