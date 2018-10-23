package org.ephyra.acropolis.persistence.api.entity

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import java.util.Arrays
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob

/**
 * Entity model to represent a graphical asset (e.g. an icon)
 */
@Entity
data class GraphicalAssetEntity @JvmOverloads constructor(
        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        @Lob
        val source: ByteArray,

        @Column(nullable = false)
        val assetType: GraphicalAssetType,

        @Column
        var description: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GraphicalAssetEntity

        if (!Arrays.equals(source, other.source)) return false
        if (description != other.description) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(source)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}