package io.huta.segments.player.player.domain

import arrow.fx.IO
import java.util.UUID

interface PlayerRepository {
    fun insert(key: UUID, value: Player): IO<Player>
    fun fetch(key: UUID): IO<Player?>
    fun delete(key: UUID): IO<Player?>
    fun update(key: UUID, value: Player): IO<Player?>
}
