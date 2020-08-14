package io.huta.segments.infrastructure.repository.player

import io.huta.segments.infrastructure.repository.InMemoryRepository
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import java.util.UUID

class InMemoryPlayerRepository(map: MutableMap<UUID, Player>) : InMemoryRepository<UUID, Player>(map), PlayerRepository
