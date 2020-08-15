package io.huta.segments.player.player.application.query

import arrow.core.Either
import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.api.dto.PlayerDto
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import java.lang.Exception
import java.util.UUID

class PlayerGet(
    private val playerRepository: PlayerRepository
) : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        when (val uuid = uuidFromParam(event)) {
            is Either.Left -> noUuidResponse(event)
            is Either.Right -> {
                when (validate(uuid.b)) {
                    is Either.Left -> noUuidResponse(event)
                    is Either.Right -> {
                        playerRepository.fetch(UUID.fromString(uuid.b)).result()?.let { pl ->
                            okResponse(event, pl)
                        } ?: noPlayerResponse(event)
                    }
                }
            }
        }
    }

    private fun okResponse(event: RoutingContext, pl: Player) {
        event.response().setStatusCode(200).end(Json.encode(PlayerDto(pl.uuid, pl.name, pl.mail)))
    }

    private fun noPlayerResponse(event: RoutingContext) {
        event.response().setStatusCode(404).end(Json.encode(ResponseErrorBody("No player with uuid present")))
    }

    private fun noUuidResponse(event: RoutingContext) {
        event.response().setStatusCode(400).end(Json.encode(ResponseErrorBody("No uuid passed in url")))
    }

    private fun uuidFromParam(event: RoutingContext): Either<Throwable, String> {
        val uuid: String? = event.request().getParam("uuid")
        return if (uuid != null) {
            Either.right(uuid)
        } else {
            Either.left(IllegalArgumentException())
        }
    }

    private fun validate(string: String) =
        try {
            Either.right(UUID.fromString(string)!!)
        } catch (ex: Exception) {
            Either.left(ex)
        }
}
