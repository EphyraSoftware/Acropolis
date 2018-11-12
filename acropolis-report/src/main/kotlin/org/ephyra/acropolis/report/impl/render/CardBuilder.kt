package org.ephyra.acropolis.report.impl.render

import java.io.InputStream

class CardBuilder(
        private val position2D: Position2D,

        private val size2D: Size2D
) {
    private var source: InputStream? = null
    private var label: String? = null

    fun withImage(source: InputStream): CardBuilder {
        this.source = source
        return this
    }

    fun withLabel(label: String): CardBuilder {
        this.label = label
        return this
    }

    fun build(renderer: DiagramRenderer) {

    }
}