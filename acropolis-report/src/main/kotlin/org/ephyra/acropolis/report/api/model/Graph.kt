package org.ephyra.acropolis.report.api.model

/**
 * Container for a graph model.
 * Allows metadata to be provided with the model.
 */
class GraphContainer(
        val graph: Graph
) {
    private val subGraphs: MutableList<SubGraphSelector> = ArrayList()

    /**
     * Define a named sub-graph of the graph held by this container.
     * The provided list of nodes can later be used to extract a sub-graph.
     *
     * @param name The name of the sub-graph
     * @param includeNodes The nodes to include in the sub-graph
     */
    fun defineSubgraph(name: String, includeNodes: List<Node>) {
        subGraphs.add(SubGraphSelector(name, includeNodes))
    }
}

/**
 * Model to represent a graph, in the mathematical sense.
 */
class Graph {
    private val nodes: MutableSet<Node> = HashSet()

    private val edges: MutableList<Edge> = ArrayList()

    /**
     * Add a node to the graph
     *
     * @param node The node to add
     */
    fun addNode(node: Node) {
        nodes.add(node)
    }

    /**
     * Add an edge between two nodes
     *
     * @param n1 Node to connect
     * @param n2 Node to connect
     */
    fun addEdge(n1: Node, n2: Node) {
        edges.add(Edge(n1, n2, false))
    }

    /**
     * Add a directed edge between two nodes
     *
     * @param from The source node for the edge
     * @param to The sink node for the edge
     */
    fun addDirectedEdge(from: Node, to: Node) {
        edges.add(Edge(from, to, true))
    }

    /**
     * Find a node by its label
     *
     * @param label The label to search for
     * @return The node, if found
     */
    fun findNode(label: String): Node? {
        return nodes.find { node -> node.label == label }
    }

    /**
     * Find nodes, N, such that all edges which connect N to the graph, N is the source.
     *
     * Only valid for di-graphs, otherwise must allow edges if incoming edges have a corresponding outgoing edge.
     */
    fun findSourceNodes(): HashSet<Node> {
        val tempNodes = HashSet<Node>()
        tempNodes.addAll(nodes)

        edges.forEach { edge ->
            val node = edge.sink
            tempNodes.remove(node)
        }

        return tempNodes
    }

    /**
     * Return which ever node happens to be first.
     *
     * @return The first node in the graph, as it is stored
     */
    fun firstNode(): Node {
        return nodes.first()
    }

    /**
     * Finds edges such that the specified node is the source, and collects the set of sink nodes
     * from these edges.
     */
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

/**
 * Model to represent a node in a graph
 */
class Node(
        val label: String
)

/**
 * Model to represent an edge in a graph
 */
class Edge(
        val source: Node,

        val sink: Node,

        val directed: Boolean = false
)

/**
 * Selector for building a sub-graph from a subset of a graph's nodes.
 */
class SubGraphSelector (
        val name: String,

        val includeNodes: List<Node>
)
