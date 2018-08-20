package org.ephyra.acropolis.persistence.api

interface IConnectable {
    fun getConnectionId(): Long
    fun getConnectionType(): Int
}

enum class ConnectionType(val type: Int) {
    APPLICATION_SOFTWARE(0),
    SYSTEM_SOFTWARE(1),
    DATASTORE(2),

    SPECIALIZATION_BASE(3),
    REVERSE_PROXY(4),
    QUEUE(5),
    LOAD_BALANCER(6);

    companion object {
        private val map = ConnectionType.values().associateBy(ConnectionType::type)
        fun fromInt(type: Int) = map[type]
    }
}
