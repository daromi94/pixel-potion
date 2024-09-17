package com.daromi.pixel.potion.core

sealed interface BlendMode {
    fun blend(
        foreground: Color,
        background: Color,
    ): Color?

    companion object {
        fun parse(raw: String): BlendMode? =
            when {
                raw.startsWith("transparency(") && raw.endsWith(")") -> Transparency(0.5) // TODO: extract alpha

                raw == "multiply" -> Multiply

                raw == "screen" -> Screen

                raw == "none" -> None

                else -> null
            }
    }
}

data class Transparency(
    val alpha: Double,
) : BlendMode {
    companion object {
        fun from(alpha: Double): Transparency? = if (alpha !in 0.0..1.0) null else Transparency(alpha)
    }

    override fun blend(
        foreground: Color,
        background: Color,
    ): Color? {
        val red   = combine(foreground.red,   background.red)   ?: return null
        val green = combine(foreground.green, background.green) ?: return null
        val blue  = combine(foreground.blue,  background.blue)  ?: return null

        return Color(red, green, blue)
    }

    private fun combine(
        first: Channel,
        second: Channel,
    ): Channel? {
        val value = first.value * this.alpha + second.value * (1 - this.alpha)

        return Channel.from(value.toInt())
    }
}

data object Multiply : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color? {
        val red   = combine(foreground.red,   background.red)   ?: return null
        val green = combine(foreground.green, background.green) ?: return null
        val blue  = combine(foreground.blue,  background.blue)  ?: return null

        return Color(red, green, blue)
    }

    private fun combine(
        first: Channel,
        second: Channel,
    ): Channel? {
        val top   = Channel.MAX
        val value = 1.0 * first.value * second.value / top.value

        return Channel.from(value.toInt())
    }
}

data object Screen : BlendMode {
    override fun blend(
        foreground: Color,
        background: Color,
    ): Color? {
        val red   = combine(foreground.red,   background.red)   ?: return null
        val green = combine(foreground.green, background.green) ?: return null
        val blue  = combine(foreground.blue,  background.blue)  ?: return null

        return Color(red, green, blue)
    }

    private fun combine(
        first: Channel,
        second: Channel,
    ): Channel? {
        val top   = Channel.MAX
        val value = top.value - 1.0 * (top.value - first.value) * (top.value - second.value) / top.value

        return Channel.from(value.toInt())
    }
}

data object None : BlendMode {
    override fun blend(foreground: Color, background: Color): Color = foreground
}
