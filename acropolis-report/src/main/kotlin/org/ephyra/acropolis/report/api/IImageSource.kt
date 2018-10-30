package org.ephyra.acropolis.report.api

import java.io.InputStream

/**
 * Image source to provide images on request to the rendering process.
 */
interface IImageSource {
    /**
     * Get an image by its resource name
     *
     * @param resourceName The resource name of the image
     * @return An input stream for the image data to be read from
     */
    fun get(resourceName: String): InputStream
}
