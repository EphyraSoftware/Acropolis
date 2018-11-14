package org.ephyra.acropolis.report.impl.render

import java.io.InputStream

/**
 * Builder to encapsulate the logic of laying out an image and associated text.
 */
@Suppress("MagicNumber")
class CardBuilder(
        private val position2D: Position2D,

        private val size2D: Size2D
) {
    private var source: InputStream? = null
    private var label: String? = null

    /**
     * Configures the image to use for the card being built.
     *
     * @param source The input stream which will provide the image data
     * @return The CardBuilder instance so that method calls can be chained
     */
    fun withImage(source: InputStream): CardBuilder {
        this.source = source
        return this
    }

    /**
     * Configures the label to use for the card being built.
     *
     * @param label The label text
     * @return The CardBuilder instance so that method calls can be chained
     */
    fun withLabel(label: String): CardBuilder {
        this.label = label
        return this
    }

    /**
     * Creates the card layout and renders it using the provided renderer.
     *
     * @param renderer The diagram renderer to use for drawing the card
     */
    fun build(renderer: DiagramRenderer) {
        renderer.drawOutline(position2D, size2D)

        val imageSource = source
        if (imageSource != null) {
            drawImage(renderer, imageSource)
        }

        val labelText = label
        if (labelText != null) {
            drawLabel(renderer, labelText)
        }
    }

    private fun drawLabel(renderer: DiagramRenderer, labelText: String) {
        val labelDimensions = renderer.getStringDimensions(labelText)

        val position = Position2D(
                (position2D.x + 0.5 * size2D.width - 0.5 * labelDimensions.width).toFloat(),
                (position2D.y + 0.9 * size2D.height - 0.5 * labelDimensions.height).toFloat()
        )

        renderer.drawString(labelText, position)
    }

    private fun drawImage(renderer: DiagramRenderer, imageSource: InputStream) {
        val imageWidthScale = 0.8
        val imageHeightScale = 0.7
        val imagePosition = Position2D(
                (position2D.x + size2D.width * (0.5 * (1 - imageWidthScale))).toFloat(),
                (position2D.y + size2D.width * (0.5 * (1 - imageWidthScale))).toFloat()
        )

        val imageSize = Size2D(
                (size2D.width * imageWidthScale).toFloat(),
                (size2D.height * imageHeightScale).toFloat()
        )

        renderer.addImage(imagePosition, imageSize, imageSource)
    }
}
