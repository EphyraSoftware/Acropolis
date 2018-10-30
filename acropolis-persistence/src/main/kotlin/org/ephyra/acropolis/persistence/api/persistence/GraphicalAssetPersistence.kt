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

    /**
     * Stores a new graphical asset in the asset repository
     *
     * @param graphicalAsset The asset to store
     */
    fun create(graphicalAsset: GraphicalAssetEntity) {
        repo.save(graphicalAsset)
    }

    /**
     * Finds all graphical assets in the asset repository
     *
     * @return Iterator for each graphical asset entity found in the repository
     */
    fun findAll(): MutableIterable<GraphicalAssetEntity> {
        return repo.findAll()
    }

    /**
     * Find a graphical asset by name
     *
     * @param name The name of the asset
     * @return the asset, if found
     */
    fun find(name: String): GraphicalAssetEntity? {
        val asset = repo.findByName(name)
        return if (asset.isPresent) asset.get() else null
    }
}
