package com.pixel.potion

import java.awt.image.BufferedImage

fun makeImage(
    width: Int,
    height: Int,
    color: Color = Color.WHITE,
): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val pixel = color.toInt()
    val pixels = IntArray(width * height) { pixel }
    image.setRGB(0, 0, width, height, pixels, 0, width)
    return image
}
