package io.huta.segments.player.player.application.query

import arrow.core.Either
import arrow.core.flatMap
import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.api.dto.PlayerDto
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import io.huta.segments.player.player.domain.exception.PlayerNotFound
import io.huta.segments.player.shared.api.exception.InvalidUuidFormat
import io.huta.segments.player.shared.api.exception.UuidAbsent
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import java.util.UUID

class PlayerGet(
    private val playerRepository: PlayerRepository
) : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        println(event)
    }
//    override fun handle(event: RoutingContext) {
//        uuidFromParam(event)
//            .flatMap { uuid -> validate(uuid) }
//            .flatMap { uuid -> fetch(uuid) }
//            .bimap(::handleError, ::handleSuccess)
//            .fold({ handleErrResponse(it, event) }, { handleOkResponse(it, event) })
//    }
//
//    private fun handleOkResponse(resp: Pair<Int, PlayerDto>, event: RoutingContext) {
//        event.response().setStatusCode(resp.first).end(Json.encode(resp.second))
//    }
//
//    private fun handleErrResponse(resp: Pair<Int, ResponseErrorBody>, event: RoutingContext) {
//        event.response().setStatusCode(resp.first).end(Json.encode(resp.second))
//    }
//
//    private fun handleSuccess(player: Player) =
//        Pair(200, PlayerDto(player.uuid, player.name, player.mail))
//
//    private fun handleError(ex: Throwable) =
//        when (ex) {
//            is UuidAbsent -> Pair(400, ResponseErrorBody("No uuid passed in url"))
//            is InvalidUuidFormat -> Pair(400, ResponseErrorBody("No uuid passed in url"))
//            is PlayerNotFound -> Pair(404, ResponseErrorBody("No player with uuid present"))
//            else -> Pair(500, ResponseErrorBody("Unkonwn error"))
//        }
//
//    private fun fetch(uuid: UUID): Either<Throwable, Player> =
//        playerRepository.fetch(uuid).result()?.let {
//            Either.right(it)
//        } ?: Either.left(PlayerNotFound())
//
//    private fun uuidFromParam(event: RoutingContext): Either<Throwable, String> {
//        val uuid: String? = event.request().getParam("uuid")
//        return if (uuid != null) {
//            Either.right(uuid)
//        } else {
//            Either.left(UuidAbsent())
//        }
//    }
//
//    private fun validate(string: String) =
//        try {
//            Either.right(UUID.fromString(string)!!)
//        } catch (ex: Exception) {
//            Either.left(InvalidUuidFormat())
//        }
}
