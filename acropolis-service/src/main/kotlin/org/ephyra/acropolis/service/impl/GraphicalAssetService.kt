package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEntity
import org.ephyra.acropolis.persistence.api.persistence.GraphicalAssetPersistence
import org.ephyra.acropolis.service.api.IGraphicalAssetService
import org.ephyra.acropolis.service.api.model.AssetType
import org.ephyra.acropolis.service.api.model.GraphicalAsset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

/**
 * Service implementation for working with graphical assets
 */
@Service
class GraphicalAssetService : IGraphicalAssetService {
    @Autowired
    private lateinit var persistence: GraphicalAssetPersistence

    override fun create(name: String, data: ByteArray, type: GraphicalAssetType) {
        persistence.create(GraphicalAssetEntity(name, data, type.type))
    }

    override fun findAll(): List<GraphicalAsset> {
        return persistence.findAll().map { assetEntity ->
            val type = when (GraphicalAssetType.fromInt(assetEntity.assetType)) {
                GraphicalAssetType.PNG -> AssetType.PNG
                else -> throw IllegalStateException("Unknown asset type")
            }

            GraphicalAsset(type, assetEntity.source)
        }
    }
}
