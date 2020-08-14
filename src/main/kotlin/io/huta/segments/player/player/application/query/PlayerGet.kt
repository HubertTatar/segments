package io.huta.segments.player.player.application.query

import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.api.dto.PlayerDto
import io.huta.segments.player.player.domain.PlayerRepository
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import java.util.UUID

class PlayerGet(
    private val playerRepository: PlayerRepository
) : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        val uuid: String? = event.request().getParam("uuid")

        uuid?.let { id ->
            validate(uuid) // TODO
            val player = playerRepository.fetch(UUID.fromString(id)).result()
            player?.let { pl ->
                event.response().setStatusCode(200).end(Json.encode(PlayerDto(pl.uuid, pl.name, pl.mail)))
            } ?: event.response().setStatusCode(404).end(Json.encode(ResponseErrorBody("No player with uuid present")))
        } ?: event.response().setStatusCode(400).end(Json.encode(ResponseErrorBody("No uuid passed in url")))
    }

    private fun validate(string: String) {
        kotlin.runCatching { UUID.fromString(string) }
    }
}
