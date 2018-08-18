package org.ephyra.acropolis.persistence.api

interface IConnectable {
    fun getConnectionId(): Long
    fun getConnectionType(): Int
}

enum class ConnectionType(val type: Int) {
    APPLICATION_SOFTWARE(0),
    SYSTEM_SOFTWARE(1),

    SPECIALIZATION_BASE(2),
    REVERSE_PROXY(3),
    LOAD_BALANCER(4);

    companion object {
        private val map = ConnectionType.values().associateBy(ConnectionType::type);
        fun fromInt(type: Int) = map[type]
    }
}
