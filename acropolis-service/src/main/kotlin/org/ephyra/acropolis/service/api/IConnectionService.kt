package org.ephyra.acropolis.service.api

interface IConnectionService {
    fun <F, T> create(fromId: Long, fromType: Class<F>, toId: Long, toType: Class<T>)
}