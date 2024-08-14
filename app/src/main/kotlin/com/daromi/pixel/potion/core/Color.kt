package com.daromi.pixel.potion.core

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.daromi.pixel.potion.util.Error

class Color private constructor(
    private val red: Channel,
    private val green: Channel,
    private val blue: Channel,
) {
    companion object {
        val WHITE = Color(Channel.MAX, Channel.MAX, Channel.MAX)
        val BLACK = Color(Channel.MIN, Channel.MIN, Channel.MIN)

        fun fromHex(value: Int): Color {
            val red = (value and 0xFF0000) shr 16
            val green = (value and 0xFF00) shr 8
            val blue = value and 0xFF

            // TODO: use "from" named constructor
            return Color(Channel(red), Channel(green), Channel(blue))
        }

        fun fromChannels(
            red: Channel,
            green: Channel,
            blue: Channel,
        ): Color = Color(red, green, blue)
    }

    fun toInt(): Int = (this.red.value shl 16) or (this.green.value shl 8) or this.blue.value
}

data class Channel(
    val value: Int,
) {
    companion object {
        val MIN = Channel(0x00)
        val MAX = Channel(0xFF)

        fun from(value: Int): Either<IllegalChannelError, Channel> =
            either {
                ensure(value in (MIN.value)..(MAX.value)) {
                    IllegalChannelError(value)
                }
                Channel(value)
            }

        class IllegalChannelError(
            private val value: Int,
        ) : Error {
            override val message: String get() = "illegal channel '${this.value}', must be in range [${MIN.value}, ${MAX.value}]"
        }
    }
}
