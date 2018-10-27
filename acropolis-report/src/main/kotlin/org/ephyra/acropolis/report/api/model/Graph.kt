package org.ephyra.acropolis.report.api.model

class GraphContainer(
        val graph: Graph
) {
    private val subGraphs: MutableList<SubGraphSelector> = ArrayList()

    fun defineSubgraph(name: String, includeNodes: List<Node>) {
        subGraphs.add(SubGraphSelector(name, includeNodes))
    }
}

class Graph {
    private val nodes: MutableSet<Node> = HashSet()

    private val edges: MutableList<Edge> = ArrayList()

    fun addNode(node: Node) {
        nodes.add(node)
    }

    fun addEdge(n1: Node, n2: Node) {
        edges.add(Edge(n1, n2, false))
    }

    fun addDirectedEdge(from: Node, to: Node) {
        edges.add(Edge(from, to, true))
    }

    fun findNode(label: String): Node? {
        return nodes.find { node -> node.label == label }
    }

    // Only valid for di-graphs, otherwise much allow edges if incoming edges have a corresponding outgoing edge.
    fun findSourceNodes(): HashSet<Node> {
        val tempNodes = HashSet<Node>()
        tempNodes.addAll(nodes)

        edges.forEach { edge ->
            val node = edge.sink
            tempNodes.remove(node)
        }

        return tempNodes
    }

    fun firstNode(): Node {
        return nodes.first()
    }

    fun findNodesConnectedFrom(node: Node): HashSet<Node> {
        val tempNodes = HashSet<Node>()
        edges.forEach { edge ->
            if (edge.source == node) {
                tempNodes.add(edge.sink)
            }
        }

        return tempNodes
    }
}

class Node(
        val label: String
)

class Edge(
        val source: Node,

        val sink: Node,

        val directed: Boolean = false
)

class SubGraphSelector (
        val name: String,

        val includeNodes: List<Node>
)
