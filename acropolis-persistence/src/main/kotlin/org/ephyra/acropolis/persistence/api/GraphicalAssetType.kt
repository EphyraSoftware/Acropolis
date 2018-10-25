package org.ephyra.acropolis.persistence.api

/**
 * Types of data which can be stored as graphical assets
 */
enum class GraphicalAssetType(val type: Int) {
    PNG(0);

    companion object {
        private val map = GraphicalAssetType.values().associateBy(GraphicalAssetType::type)

        /**
         * Lookup a graphical asset type enumeration value from its integer key
         */
        fun fromInt(type: Int) = map[type]
    }
}
