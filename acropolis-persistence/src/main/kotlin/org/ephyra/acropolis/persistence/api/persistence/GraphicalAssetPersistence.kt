package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEnttiy
import org.ephyra.acropolis.persistence.impl.GraphicalAssetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GraphicalAssetPersistence {
    @Autowired
    private lateinit var repo: GraphicalAssetRepository

    fun create(graphicalAsset: GraphicalAssetEnttiy) {
        repo.save(graphicalAsset)
    }
}
