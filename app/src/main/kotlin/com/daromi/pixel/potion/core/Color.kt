package com.daromi.pixel.potion.core

data class Color(val red: Channel, val green: Channel, val blue: Channel) {

  companion object {
    val WHITE = Color(Channel.MAX, Channel.MAX, Channel.MAX)
    val BLACK = Color(Channel.MIN, Channel.MIN, Channel.MIN)

    fun from(value: UInt): Color {
      val red   = Channel.compact(value and 0xFF0000u shr 16)
      val green = Channel.compact(value and 0x00FF00u shr 8)
      val blue  = Channel.compact(value and 0x0000FFu)

      return Color(red, green, blue)
    }
  }

  fun toUInt(): UInt {
    val high = this.red.expand()   shl 16
    val mid  = this.green.expand() shl 8
    val low  = this.blue.expand()

    return high or mid or low
  }
}

@JvmInline
value class Channel(val value: UByte) {

  companion object {
    val MIN = Channel(0x00u)
    val MAX = Channel(0xFFu)

    fun compact(value: UInt): Channel = Channel(value.toUByte())
  }

  fun expand(): UInt = this.value.toUInt()

  override fun toString(): String {
    val hex = Integer.toHexString(this.value.toInt())

    return "Channel($hex)"
  }
}
