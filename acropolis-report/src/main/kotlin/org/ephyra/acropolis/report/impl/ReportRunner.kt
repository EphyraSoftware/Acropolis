package org.ephyra.acropolis.report.impl

import org.ephyra.acropolis.report.api.IImageSource
import org.ephyra.acropolis.report.api.IReportRunner
import org.ephyra.acropolis.report.api.model.Graph
import org.ephyra.acropolis.report.api.model.GraphContainer
import org.ephyra.acropolis.report.api.model.Node
import org.ephyra.acropolis.report.impl.render.DiagramRenderer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File
import java.lang.IllegalStateException

/**
 * Implementation of the report runner interface.
 */
@Suppress("MagicNumber")
@Component
internal class ReportRunner : IReportRunner {
    private val logger = LoggerFactory.getLogger(ReportRunner::class.java)

    override fun run(graphContainer: GraphContainer, imageSource: IImageSource) {
        println("Running report")
        val depthMap = buildNodeDepth(graphContainer.graph)
        val depthCounts = countDepths(depthMap)

        val maxDepth = depthMap.values.max() ?: throw IllegalStateException("missing depth")
        val maxDepthCount = depthCounts.values.max() ?: throw IllegalStateException("missing count")

        val tileWidth = 300
        val tileHeight = 350

        val cardSeparationHorizontal = 75
        val cardSeparationVertical = 35

        val diagramPadding = 30

        val diagramWidth = 2 * diagramPadding + (maxDepth + 1) * tileWidth + maxDepth * cardSeparationHorizontal
        val diagramHeight = 2 * diagramPadding + maxDepthCount * tileHeight
                + (maxDepthCount - 1) * cardSeparationVertical

        logger.debug("Creating diagram with dimensions [w=$diagramWidth, h=$diagramHeight]")

        val tempDepthCounts = HashMap<Int, Int>()
        depthCounts.forEach { depth, count ->
            tempDepthCounts[depth] = count
        }

        val positions = HashMap<Node, Position>()
        depthMap.forEach { node, depth ->
            val currentDepthCount = tempDepthCounts[depth] ?: throw IllegalStateException("missing temp depth count")

            val x = diagramPadding + depth * cardSeparationHorizontal + depth * tileWidth

            val y = diagramPadding + (currentDepthCount - 1) * tileHeight
                + (currentDepthCount - 1) * cardSeparationVertical

            val position = Position(
                    x.toFloat(),
                    y.toFloat()
            )

            logger.debug("Placing node [label=${node.label}] at position [x=$x, y=$y")

            positions[node] = position

            tempDepthCounts[depth] = currentDepthCount - 1
        }

        DiagramRenderer(diagramWidth, diagramHeight).use { renderer ->
            positions.forEach { node, position ->
                val imageResource = imageSource.get(node.representedByResourceName)
                renderer.addImage(position.x.toInt(), position.y.toInt(), imageResource)
            }
            renderer.export(File("test-report.png"))
        }
    }

    private fun countDepths(depthMap: HashMap<Node, Int>): HashMap<Int, Int> {
        val depthCounts = HashMap<Int, Int>()
        depthMap.forEach { (_, v) ->
            val t = depthCounts[v]
            if (t == null) {
                depthCounts[v] = 1
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

/**
 * Represents a position on a 2D plane
 */
class Position(
        val x: Float,
        val y: Float
)
