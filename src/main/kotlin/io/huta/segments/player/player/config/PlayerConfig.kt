package io.huta.segments.player.player.config

import io.huta.segments.infrastructure.repository.InMemoryHandlerRepository
import io.huta.segments.player.player.application.handler.PlayerRegister
import io.huta.segments.player.player.application.query.PlayerGet
import io.huta.segments.player.player.domain.PlayerRepository
import io.huta.segments.player.player.infrastructure.InMemoryPlayerRepository

class PlayerConfig {

    private val playerRepository: PlayerRepository =
        InMemoryPlayerRepository(InMemoryHandlerRepository(HashMap()))

    val playerGet = PlayerGet(playerRepository)

    val registerPlayer = PlayerRegister(playerRepository)
}
