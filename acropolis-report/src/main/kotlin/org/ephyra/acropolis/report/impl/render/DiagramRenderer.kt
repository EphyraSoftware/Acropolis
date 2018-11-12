package org.ephyra.acropolis.report.impl.render

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Polygon
import java.awt.Rectangle
import java.awt.Stroke
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import java.io.InputStream
import java.lang.IllegalStateException
import javax.imageio.ImageIO

/**
 * Wrapper around Java2D API operations to provide a specific set of operations for use
 * in diagram rendering.
 */
@Suppress("MagicNumber")
class DiagramRenderer(
        width: Int,

        height: Int
) : AutoCloseable {
    private val targetImg: BufferedImage = BufferedImage(width, height, TYPE_INT_RGB)
    private val target: Graphics2D
    private var font: Font

    init {
        target = targetImg.createGraphics()

        target.color = Color.WHITE
        target.fillRect(0, 0, width, height)

        font = Font.createFont(Font.TRUETYPE_FONT, File("user/staging/playfair-display/PlayfairDisplay-Regular.ttf"))
        font = font.deriveFont(target.font.size * 1.2f)
    }

    /**
     * Add an image to the diagram at the specified coordinates
     *
     * @param positionX The offset of the left side of the image from the left side of the diagram
     * @param positionY The offset of the top side of the image from the top side of the diagram
     * @param source The input stream to read the image from
     */
    fun addImage(position: Position2D, size: Size2D, source: InputStream) {
        val img = ImageIO.read(source)
        target.drawImage(img, position.x.toInt(), position.y.toInt(), size.width.toInt(), size.height.toInt(), null)
    }

    /**
     * Export the diagram to file
     *
     * @param outFile The file to write to
     */
    fun export(outFile: File) {
        ImageIO.write(targetImg, outFile.extension, outFile)
    }

    /**
     * Draw a connection between two tiles.
     */
    fun drawConnection() {
        target.stroke = BasicStroke(4f)
        target.color = Color.BLUE
        target.drawLine(895, 550, 895, 800 - 20)

        target.stroke = BasicStroke(2f)
        target.color = Color.YELLOW
        val polygon = Polygon()
        polygon.addPoint(895, 800)
        polygon.addPoint(895 - 20, 800 - 20)
        polygon.addPoint(895 + 20, 800 - 20)
        target.fill(polygon)
        target.drawPolygon(polygon)
    }

    /**
     * Draw a string of text onto the diagram using the given font.
     *
     * @param str The text to draw
     * @param fontFile The file from which to load a font for use in the rendering
     */
    fun drawString(str: String, position: Position2D) {
        target.color = Color.DARK_GRAY
        target.font = font
        target.drawString(str, position.x, position.y)
    }

    fun getStringDimensions(str: String): Size2D {
        val fontMetrics = target.getFontMetrics(font)

        val bounds = fontMetrics.getStringBounds(str, target)

        return Size2D(bounds.width.toFloat(), bounds.height.toFloat())
    }

    fun drawOutline(position: Position2D, size: Size2D) {
        target.background = Color.YELLOW
        target.stroke = BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)

        target.drawRect(position.x.toInt(), position.y.toInt(), size.width.toInt(), size.height.toInt())
    }

    override fun close() {
        target.dispose()
    }
}
