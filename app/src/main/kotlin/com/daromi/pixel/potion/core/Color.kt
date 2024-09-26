package com.daromi.pixel.potion.core

data class Color(
    val red: Channel,
    val green: Channel,
    val blue: Channel,
) {
    companion object {
        val WHITE = Color(Channel.MAX, Channel.MAX, Channel.MAX)
        val BLACK = Color(Channel.MIN, Channel.MIN, Channel.MIN)

        fun from(value: Int): Color? {
            val red = Channel.from((value and 0xFF0000) shr 16) ?: return null
            val green = Channel.from((value and 0x00FF00) shr 8) ?: return null
            val blue = Channel.from(value and 0x0000FF) ?: return null

            return Color(red, green, blue)
        }
    }

    fun toInt(): Int = (this.red.value shl 16) or (this.green.value shl 8) or this.blue.value
}

@JvmInline
value class Channel(
    val value: Int,
) {
    companion object {
        val MIN = Channel(0x00)
        val MAX = Channel(0xFF)

        fun from(value: Int): Channel? = if (value !in (MIN.value)..(MAX.value)) null else Channel(value)
    }
}
