package com.daromi.pixel.potion.core

sealed interface BlendMode {
  fun blend(c1: Color, c2: Color): Color
}

class Transparency private constructor(private val alpha: Float) : BlendMode {

  companion object {
    fun from(alpha: Float): Transparency? = if (alpha !in 0.0f..1.0f) null else Transparency(alpha)
  }

  override fun blend(
      c1: Color,
      c2: Color,
  ): Color {
    val red = combine(c1.red, c2.red)
    val green = combine(c1.green, c2.green)
    val blue = combine(c1.blue, c2.blue)

    return Color(red, green, blue)
  }

  private fun combine(
      ch1: Channel,
      ch2: Channel,
  ): Channel {
    val value = ch1.value.toFloat() * this.alpha + ch2.value.toFloat() * (1.0f - this.alpha)

    return Channel.compact(value.toUInt())
  }
}

data object Multiply : BlendMode {

  override fun blend(
      c1: Color,
      c2: Color,
  ): Color {
    val red = combine(c1.red, c2.red)
    val green = combine(c1.green, c2.green)
    val blue = combine(c1.blue, c2.blue)

    return Color(red, green, blue)
  }

  private fun combine(
      ch1: Channel,
      ch2: Channel,
  ): Channel {
    val value = 1.0f * ch1.value.toFloat() * ch2.value.toFloat() / 0xFF

    return Channel.compact(value.toUInt())
  }
}

data object Screen : BlendMode {

  override fun blend(
      c1: Color,
      c2: Color,
  ): Color {
    val red = combine(c1.red, c2.red)
    val green = combine(c1.green, c2.green)
    val blue = combine(c1.blue, c2.blue)

    return Color(red, green, blue)
  }

  private fun combine(
      ch1: Channel,
      ch2: Channel,
  ): Channel {
    val value = 0xFF - 1.0f * (0xFF - ch1.value.toFloat()) * (0xFF - ch2.value.toFloat()) / 0xFF

    return Channel.compact(value.toUInt())
  }
}

data object None : BlendMode {
  override fun blend(c1: Color, c2: Color): Color = c1
}
