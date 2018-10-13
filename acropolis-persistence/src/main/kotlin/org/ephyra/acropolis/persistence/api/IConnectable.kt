package org.ephyra.acropolis.persistence.api

/**
 * Interface for describing one end of a point-to-point connection
 */
interface IConnectable {
    /**
     * The unique id for this connection
     */
    fun getConnectionId(): Long

    /**
     * The type of item on the current end of the connection
     */
    fun getConnectionEndpointType(): Int
}

/**
 * Enum defining the allowed connection types between entities
 */
enum class ConnectionType(val type: Int) {
    HOSTED_BY(0),
    TALKS_TO(1);
}

/**
 * Enum defining the types that are allowed to be connected up
 */
@Suppress("MagicNumber")
enum class ConnectionEndpointType(val type: Int) {
    APPLICATION_SOFTWARE(0),
    SYSTEM_SOFTWARE(1),
    DATASTORE(2),
    COMPUTE_INSTANCE(3),

    SPECIALIZATION_BASE(4),
    REVERSE_PROXY(5),
    QUEUE(6),
    LOAD_BALANCER(7);

    companion object {
        private val map = ConnectionEndpointType.values().associateBy(ConnectionEndpointType::type)

        /**
         * Lookup a connection endpoint type enumeration value from its integer key
         */
        fun fromInt(type: Int) = map[type]
    }
}
