package org.ephyra.acropolis.persistence.api

interface IConnectable {
    fun getConnectionId(): Long
    fun getConnectionType(): Int
}

enum class ConnectionType(val type: Int) {
    APPLICATION_SOFTWARE(0),
    SYSTEM_SOFTWARE(1)
}