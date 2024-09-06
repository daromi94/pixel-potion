package com.daromi.pixel.potion.core

import kotlin.math.max
import kotlin.math.min

sealed interface BlendMode {
    fun blend(
        foreground: Color,
        background: Color,
    ): Color

    companion object {
        fun parse(raw: String): BlendMode =
            // TODO: exception handling (i.e., nullable, result, etc.)
            when {
                raw.startsWith("transparency(") && raw.endsWith(")") -> Transparency(0.5) // TODO: extract alpha

                raw == "multiply" -> Multiply

                raw == "screen" -> Screen

                else -> None
            }
    }
}

/*
  REVIEW:
  - should this class be a data class?
  - should this class use a factory method and make the constructor private?
  - does this class have identity?
  - should this class be able to instantiate with a set alpha and later change?
 */
class Transparency(
    alpha: Double,
) : BlendMode {
    private val alpha: Double = min(max(alpha, 0.0), 1.0)

    override fun blend(
        foreground: Color,
        background: Color,
    ): Color {
        val red = combine(foreground.red, background.red)
        val green = combine(foreground.green, background.green)
        val blue = combine(foreground.blue, background.blue)

        return Color(red, green, blue)
    }

    private fun combine(
        first: Channel,
        second: Channel,
    ): Channel {
        val value = first.value * this.alpha + second.value * (1 - this.alpha)
        return Channel(value.toInt())
    }
}

data object Multiply : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color {
        val red = combine(foreground.red, background.red)
        val green = combine(foreground.green, background.green)
        val blue = combine(foreground.blue, background.blue)

        return Color(red, green, blue)
    }

    private fun combine(
        first: Channel,
        second: Channel,
    ): Channel {
        val top = Channel.MAX
        val value = first.value * second.value / top.value
        return Channel(value)
    }
}

data object Screen : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color {
        val red = combine(foreground.red, background.red)
        val green = combine(foreground.green, background.green)
        val blue = combine(foreground.blue, background.blue)

        return Color(red, green, blue)
    }

    private fun combine(
        first: Channel,
        second: Channel,
    ): Channel {
        val top = Channel.MAX
        val value = top.value - (top.value - first.value) * (top.value - second.value) / top.value
        return Channel(value)
    }
}

data object None : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color = foreground
}
