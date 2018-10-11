package org.ephyra.acropolis.persistence.api

interface IConnectable {
    fun getConnectionId(): Long
    fun getConnectionEndpointType(): Int
}

/**
 * Enum defining the allowed connection types between entities
 * */
enum class ConnectionType(val type: Int) {
    HOSTED_BY(0),
    TALKS_TO(1);
}

/**
 * Enum defining the types that are allowed to be connected up
 * */
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
        fun fromInt(type: Int) = map[type]
    }
}