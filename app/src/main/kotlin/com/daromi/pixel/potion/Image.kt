package com.daromi.pixel.potion

import java.awt.image.BufferedImage
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO

class Image private constructor(
    private val buffer: BufferedImage,
) {
    private val width: Int = buffer.width
    private val height: Int = buffer.height

    companion object {
        @JvmStatic
        fun read(input: InputStream): Image? =
            try {
                val buffer = ImageIO.read(input)
                Image(buffer)
            } catch (_: Exception) {
                null
            }
    }

    fun write(output: OutputStream) {
        // TODO: return type, format name, and exception handling (i.e., boolean, exception, result, etc.)
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
