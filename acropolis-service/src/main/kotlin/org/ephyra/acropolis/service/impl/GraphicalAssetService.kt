package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEntity
import org.ephyra.acropolis.persistence.api.persistence.GraphicalAssetPersistence
import org.ephyra.acropolis.service.api.IGraphicalAssetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GraphicalAssetService : IGraphicalAssetService {
    @Autowired
    private lateinit var persistence: GraphicalAssetPersistence

    override fun create(name: String, data: ByteArray, type: GraphicalAssetType) {
        persistence.create(GraphicalAssetEntity(name, data, type))
    }
}
