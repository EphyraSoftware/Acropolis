package org.ephyra.acropolis.service.api

interface IConnectionService {
    fun <F: Any, T: Any> create(fromId: Long, fromType: F, toId: Long, toType: T)
}