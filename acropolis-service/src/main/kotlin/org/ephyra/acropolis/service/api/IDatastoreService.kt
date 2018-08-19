package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity

interface IDatastoreService {
    fun create(projectId: Int, name: String)
    fun get(name: String): DatastoreEntity?
} 
