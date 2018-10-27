package org.ephyra.acropolis.report.impl.render

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import javax.imageio.ImageIO

class DiagramRenderer(
        private val width: Int,

        private val height: Int
) : AutoCloseable {
    private val targetImg: BufferedImage = BufferedImage(width, height, TYPE_INT_RGB)
    private val target: Graphics2D

    init {
        target = targetImg.createGraphics()

        target.color = Color.WHITE
        target.fillRect(0, 0, width, height)
    }

    fun addImage(positionX: Int, positionY: Int, source: File) {
        val img = ImageIO.read(source)
        target.drawImage(img, positionX, positionY, img.width, img.height, null)
    }

    fun export(outFile: File) {
        ImageIO.write(targetImg, outFile.extension, outFile)
    }

    override fun close() {
        target.dispose()
    }
}
