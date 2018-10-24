package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEntity
import org.ephyra.acropolis.persistence.impl.GraphicalAssetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Wrapper around GraphicalAssetRepository operations.
 */
@Component
class GraphicalAssetPersistence {
    @Autowired
    private lateinit var repo: GraphicalAssetRepository

    fun create(graphicalAsset: GraphicalAssetEntity) {
        repo.save(graphicalAsset)
    }

    fun findAll(): MutableIterable<GraphicalAssetEntity> {
        return repo.findAll()
    }
}
