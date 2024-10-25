package com.daromi.pixel.potion.core

import com.daromi.pixel.potion.util.not
import java.awt.image.BufferedImage
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO

class Image private constructor(private val buffer: BufferedImage) {

  companion object {
    fun read(input: InputStream): Image {
      val buffer = ImageIO.read(input)

      return Image(buffer)
    }
  }

  val width: UInt
    get() = this.buffer.width.toUInt()

  val height: UInt
    get() = this.buffer.height.toUInt()

  fun crop(
      x: UInt,
      y: UInt,
      width: UInt,
      height: UInt,
  ): Image? {
    // Check bounds
    if (x + width > this.width || y + height > this.height) {
      return null
    }

    // Extract pixels
    val area   = width * height
    val pixels = IntArray(!area)

    for (j in y..<y + height) {
      for (i in x..<x + width) {
        val index = (j - y) * width + (i - x)
        pixels[!index] = this.buffer.getRGB(!i, !j)
      }
    }

    // Create image
    val buffer = BufferedImage(!width, !height, this.buffer.type)
    buffer.setRGB(0, 0, !width, !height, pixels, 0, !width)

    return Image(buffer)
  }

  fun write(output: OutputStream) {
    ImageIO.write(this.buffer, "JPG", output)
  }
}
