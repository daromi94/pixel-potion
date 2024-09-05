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
            when (raw) {
                "transparency" -> Transparency(0.5) // TODO: extract alpha
                "multiply" -> Multiply
                "screen" -> Screen
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
    ): Color =
        Color(
            combine(foreground.red, background.red),
            combine(foreground.green, background.green),
            combine(foreground.blue, background.blue),
        )

    private fun combine(
        foreground: Channel,
        background: Channel,
    ): Channel {
        val value = foreground.value * this.alpha + background.value * (1 - this.alpha)
        return Channel(value.toInt())
    }
}

data object Multiply : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color =
        Color(
            combine(foreground.red, background.red),
            combine(foreground.green, background.green),
            combine(foreground.blue, background.blue),
        )

    private fun combine(
        foreground: Channel,
        background: Channel,
    ): Channel {
        val top = Channel.MAX
        val value = foreground.value * background.value / top.value
        return Channel(value)
    }
}

data object Screen : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color =
        Color(
            combine(foreground.red, background.red),
            combine(foreground.green, background.green),
            combine(foreground.blue, background.blue),
        )

    private fun combine(
        foreground: Channel,
        background: Channel,
    ): Channel {
        val top = Channel.MAX
        val value = top.value - (top.value - foreground.value) * (top.value - background.value) / top.value
        return Channel(value)
    }
}

data object None : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color = foreground
}
