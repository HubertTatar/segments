package io.huta.segments.player.player.config

import io.huta.segments.player.player.application.query.PlayerGet
import io.huta.segments.player.player.application.handler.PlayerRegister

class PlayerConfig {

    fun playerGet() = PlayerGet()

    fun registerPlayer() = PlayerRegister()
}
