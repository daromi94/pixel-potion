package com.pixel.potion

class Color(
    private val red: Int,
    private val green: Int,
    private val blue: Int,
) {
    init {
        when {
            this.red !in 0x00..0xFF -> throw IllegalArgumentException("red channel must be in range [0, 255]")

            this.green !in 0x00..0xFF -> throw IllegalArgumentException("green channel must be in range [0, 255]")

            this.blue !in 0x00..0xFF -> throw IllegalArgumentException("blue channel must be in range [0, 255]")
        }
    }

    companion object {
        val WHITE = Color(0xFF, 0xFF, 0xFF)
        val BLACK = Color(0x00, 0x00, 0x00)

        @JvmStatic
        fun fromHex(value: Int): Color {
            val red = (value and 0xFF0000) shr 16
            val green = (value and 0xFF00) shr 8
            val blue = value and 0xFF
            return Color(red, green, blue)
        }
    }

    fun toInt(): Int = (this.red shl 16) or (this.green shl 8) or this.blue
}
