package org.ephyra.acropolis.report.api

import java.io.InputStream

interface IImageSource {
    fun get(resourceName: String): InputStream
}
