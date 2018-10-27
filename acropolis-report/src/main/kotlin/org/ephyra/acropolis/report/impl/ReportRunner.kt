package org.ephyra.acropolis.report.impl

import org.ephyra.acropolis.report.api.IReportRunner
import org.ephyra.acropolis.report.api.model.Graph
import org.ephyra.acropolis.report.api.model.GraphContainer
import org.ephyra.acropolis.report.api.model.Node
import org.springframework.stereotype.Component

@Component
private class ReportRunner : IReportRunner {
    override fun run(graphContainer: GraphContainer) {
        println("Running report")
        buildNodeDepth(graphContainer.graph)

        
    }

    fun buildNodeDepth(graph: Graph): HashMap<Node, Int> {
        val startNode = pickStartNode(graph)

        val depths = HashMap<Node, Int>()
        depths[startNode] = 0

        nodeDepth(startNode, graph, depths, 1)

        return depths
    }

    fun nodeDepth(currentNode: Node, graph: Graph, depths: HashMap<Node, Int>, depth: Int) {
        val connected = graph.findNodesConnectedFrom(currentNode)

        // Remove all the nodes which are already numbered.
        connected.removeAll(depths.keys)

        // Number each of the connected nodes.
        connected.forEach { con ->
            depths[con] = depth
        }

        // Now apply the same process to each of the nodes we just numbered.
        connected.forEach { con ->
            nodeDepth(con, graph, depths, depth + 1)
        }
    }

    fun pickStartNode(graph: Graph): Node {
        val sourceNodes = graph.findSourceNodes()
        return if (sourceNodes.isEmpty()) {
            graph.firstNode()
        }
        else {
            sourceNodes.first()
        }
    }
}
