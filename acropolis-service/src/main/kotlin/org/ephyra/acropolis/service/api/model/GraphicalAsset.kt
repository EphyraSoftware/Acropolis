package org.ephyra.acropolis.service.api.model

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import java.util.Arrays

/**
 * Model to represent a graphical asset.
 */
data class GraphicalAsset(
        val type: AssetType,

        val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GraphicalAsset

        if (type != other.type) return false
        if (!Arrays.equals(data, other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + Arrays.hashCode(data)
        return result
    }
}
