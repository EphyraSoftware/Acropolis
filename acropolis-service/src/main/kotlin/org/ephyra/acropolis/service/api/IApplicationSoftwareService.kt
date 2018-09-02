package org.ephyra.acropolis.service.api

interface IApplicationSoftwareService {
    fun create(projectId: Long, hostId : Long?, name: String)
}