package org.ephyra.acropolis.service.api

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEntity
import org.ephyra.acropolis.service.api.model.GraphicalAsset

/**
 * Service for managing graphical assets. These assets are intended for use in diagram rendering.
 */
interface IGraphicalAssetService {
    /**
     * Create a new asset record for later use.
     *
     * @param name The name which will be used to identify the asset
     * @param type Enum identifier for the format of the data to expect in the data field
     * @param data The raw data for the asset
     */
    fun create(name: String, data: ByteArray, type: GraphicalAssetType)

    /**
     * Finds all available graphical assets
     *
     * @return list of graphical assets
     */
    fun findAll(): List<GraphicalAsset>

    fun find(name: String): GraphicalAssetEntity?
}
