package io.huta.segments.player.player.infrastructure

import io.huta.segments.infrastructure.repository.InMemoryHandlerRepository
import io.huta.segments.infrastructure.repository.InMemoryRepository
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.spi.FutureFactory
import java.util.*

class InMemoryPlayerRepository(
    inMemoryHandlerRepository: InMemoryHandlerRepository<UUID, Player>
) : InMemoryRepository<UUID, Player>(inMemoryHandlerRepository), PlayerRepository