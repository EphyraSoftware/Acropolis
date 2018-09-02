package org.ephyra.acropolis.service.api

interface IApplicationSoftwareService {
    fun create(projectId : Long, hostname: String?, name: String)
}