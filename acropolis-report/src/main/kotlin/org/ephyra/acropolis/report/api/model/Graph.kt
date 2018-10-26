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
}

class Node(
        val label: String
) {
}

class Edge(
        val source: Node,

        val sink: Node,

        val directed: Boolean = false
) {
}

class SubGraphSelector (
        val name: String,

        val includeNodes: List<Node>
) {
}
