package io.huta.segments.player.player.application.query

import arrow.core.Either
import arrow.core.flatMap
import arrow.fx.IO
import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.api.dto.PlayerDto
import io.huta.segments.player.player.application.handler.PlayerRegister
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import io.huta.segments.player.player.domain.exception.PlayerNotFound
import io.huta.segments.player.shared.api.exception.InvalidUuidFormat
import io.huta.segments.player.shared.api.exception.UuidAbsent
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

class PlayerGet(
    private val playerRepository: PlayerRepository
) : Handler<RoutingContext> {

    //TODO - fix either
    override fun handle(event: RoutingContext) {
        uuidFromParam(event)
            .flatMap { uuid -> validate(uuid) }
            .map { uuid -> fetch(uuid) }
            .map { player -> player.unsafeRunAsync { either ->
                log.info("unsafe")
                when (either) {
                    is Either.Right -> either.b?.let {
                        handleOkResponse(handleSuccess(it), event)
                    } ?: handleErrResponse(handleError(PlayerNotFound()), event)
                    is Either.Left -> handleErrResponse(handleError(either.a), event)
                }
            }}
            .mapLeft { ex ->
                log.error("<Player>", ex)
                handleErrResponse(handleError(ex), event)
            }
    }

    private fun handleOkResponse(resp: Pair<Int, PlayerDto>, event: RoutingContext) {
        event.response().setStatusCode(resp.first).end(Json.encode(resp.second))
    }

    private fun handleErrResponse(resp: Pair<Int, ResponseErrorBody>, event: RoutingContext) {
        event.response().setStatusCode(resp.first).end(Json.encode(resp.second))
    }

    private fun handleSuccess(player: Player) =
        Pair(200, PlayerDto(player.uuid, player.name, player.email))

    private fun handleError(ex: Throwable) =
        when (ex) {
            is UuidAbsent -> Pair(400, ResponseErrorBody("No uuid passed in url"))
            is InvalidUuidFormat -> Pair(400, ResponseErrorBody("No uuid passed in url"))
            is PlayerNotFound -> Pair(404, ResponseErrorBody("No player with uuid present"))
            else -> Pair(500, ResponseErrorBody("Unknown error"))
        }

    private fun fetch(uuid: UUID): IO<Player?> =
        playerRepository.fetch(uuid)

    private fun uuidFromParam(event: RoutingContext): Either<Throwable, String> {
        val uuid: String? = event.request().getParam("uuid")
        return if (uuid != null) {
            Either.right(uuid)
        } else {
            Either.left(UuidAbsent())
        }
    }

    private fun validate(string: String) =
        try {
            Either.right(UUID.fromString(string)!!)
        } catch (ex: Exception) {
            Either.left(InvalidUuidFormat())
        }

    companion object {
        val log: Logger = LoggerFactory.getLogger(PlayerGet::class.java)
    }
}
