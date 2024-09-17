package com.daromi.pixel.potion.core

import java.awt.image.BufferedImage
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO

class Image private constructor(
    private val buffer: BufferedImage,
) {
    companion object {
        fun read(input: InputStream): Image {
            val buffer = ImageIO.read(input)

            return Image(buffer)
        }
    }

    val width: Int get() = this.buffer.width

    val height: Int get() = this.buffer.height

    fun write(output: OutputStream) {
        ImageIO.write(this.buffer, "JPG", output)
    }

    fun crop(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
    ): Image? {
        if (x < 0 || width < 0 || x + width > this.width || y < 0 || height < 0 || y + height > this.height) {
            return null
        }

        val pixels = IntArray(width * height)

        for (j in y..<y + height) {
            for (i in x..<x + width) {
                pixels[(j - y) * width + (i - x)] = this.buffer.getRGB(i, j)
            }
        }

        val buffer = BufferedImage(width, height, this.buffer.type)
        buffer.setRGB(0, 0, width, height, pixels, 0, width)

        return Image(buffer)
    }
}
