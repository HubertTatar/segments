package io.huta.segments.player.player

import io.huta.segments.player.player.handler.PlayerGet
import io.huta.segments.player.player.handler.PlayerRegister

class PlayerConfig {

    fun playerGet() = PlayerGet()

    fun registerPlayer() = PlayerRegister()
}
