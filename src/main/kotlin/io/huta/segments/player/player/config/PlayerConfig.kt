package io.huta.segments.player.player.config

import io.huta.segments.player.player.infrastructure.InMemoryPlayerRepository
import io.huta.segments.player.player.application.query.PlayerGet
import io.huta.segments.player.player.application.handler.PlayerRegister
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import java.util.UUID
import kotlin.collections.HashMap

class PlayerConfig {

    fun playerRepository(): PlayerRepository =
        InMemoryPlayerRepository(HashMap<UUID, Player>())

    fun playerGet() = PlayerGet(playerRepository())

    fun registerPlayer() = PlayerRegister(playerRepository())
}
