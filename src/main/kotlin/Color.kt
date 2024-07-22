package com.pixel.potion

class Color(
    private val red: Int,
    private val green: Int,
    private val blue: Int,
) {
    init {
        if (this.red !in 0x00..0xFF) {
            throw IllegalArgumentException("red channel must be between 0 and 255")
        }
        if (this.green !in 0x00..0xFF) {
            throw IllegalArgumentException("green channel must be between 0 and 255")
        }
        if (this.blue !in 0x00..0xFF) {
            throw IllegalArgumentException("blue channel must be between 0 and 255")
        }
    }

    companion object {
        val WHITE = Color(0xFF, 0xFF, 0xFF)
        val BLACK = Color(0x00, 0x00, 0x00)
    }

    fun toInt(): Int = (this.red shl 16) or (this.green shl 8) or this.blue
}
