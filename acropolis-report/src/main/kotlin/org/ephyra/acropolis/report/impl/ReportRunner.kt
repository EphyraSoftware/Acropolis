package org.ephyra.acropolis.report.impl

import org.ephyra.acropolis.report.api.IReportRunner
import org.ephyra.acropolis.report.api.model.Graph
import org.ephyra.acropolis.report.api.model.GraphContainer
import org.ephyra.acropolis.report.api.model.Node
import org.ephyra.acropolis.report.impl.render.DiagramRenderer
import org.springframework.stereotype.Component
import java.lang.IllegalStateException

@Component
private class ReportRunner : IReportRunner {
    override fun run(graphContainer: GraphContainer) {
        println("Running report")
        val depthMap = buildNodeDepth(graphContainer.graph)
        val depthCounts = countDepths(depthMap)

        val maxDepth = depthMap.values.max() ?: throw IllegalStateException("missing depth")
        val maxCountAtDepth = depthCounts.values.max() ?: throw IllegalStateException("missing count")

        val tileWidth = 300
        val tileHeight = 350

        val cardSeparationHorizontal = 75
        val cardSeparationVertical = 35

        val diagramPadding = 30

        val diagramWidth = 2 * diagramPadding + (maxDepth + 1) * tileWidth + maxDepth * cardSeparationHorizontal
        val diagramHeight = 2 * diagramPadding + (maxCountAtDepth + 1) * tileHeight + maxCountAtDepth * cardSeparationVertical

        DiagramRenderer(diagramWidth, diagramHeight).use { renderer ->

        }
    }

    private fun countDepths(depthMap: HashMap<Node, Int>): HashMap<Int, Int> {
        val depthCounts = HashMap<Int, Int>()
        depthMap.forEach { (_, v) ->
            val t = depthCounts[v]
            if (t == null) {
                depthCounts[v] = 0
            } else {
                depthCounts[v] = t + 1
            }
        }
        return depthCounts
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

class Position(
        val x: Float,
        val y: Float
)
