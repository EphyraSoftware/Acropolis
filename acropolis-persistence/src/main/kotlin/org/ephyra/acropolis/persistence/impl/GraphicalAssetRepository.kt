package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional

/**
 * Repository for storing graphical assets
 */
interface GraphicalAssetRepository : CrudRepository<GraphicalAssetEntity, Long> {
    fun findByName(name: String): Optional<GraphicalAssetEntity>
}
