package io.huta.segments.player.player.domain

import io.vertx.core.AsyncResult
import java.util.UUID

interface PlayerRepository {
    fun insert(key: UUID, value: Player): AsyncResult<Player>
    fun fetch(key: UUID): AsyncResult<Player?>
    fun delete(key: UUID): AsyncResult<Player?>
    fun update(key: UUID, value: Player): AsyncResult<Player?>
}
