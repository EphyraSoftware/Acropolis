package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.report.api.IImageSource
import org.ephyra.acropolis.service.api.IGraphicalAssetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.IllegalStateException

/**
 * Implementation of the image source for use with the report module.
 * The implementation makes use of the graphical asset service to load requested resources from the database.
 */
@Component
class GraphicalAssetImageSource : IImageSource {
    @Autowired
    private lateinit var graphicalAssetService: IGraphicalAssetService

    override fun get(resourceName: String): InputStream {
        val asset = graphicalAssetService.find(resourceName)
                ?: throw IllegalStateException("Missing resource [$resourceName]")
        return ByteArrayInputStream(asset.source)
    }
}
