package io.huta.segments.player.player.application.handler

import arrow.core.Either
import arrow.fx.IO
import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.api.command.CreatePlayerCmd
import io.huta.segments.player.player.api.dto.PlayerDto
import io.huta.segments.player.player.api.validation.validate
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PlayerRegister(private val playerRepository: PlayerRepository) : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        runCatching { Json.decodeValue(event.bodyAsString, CreatePlayerCmd::class.java) }
            .onSuccess { cmd -> process(cmd, event) }
            .onFailure { ex ->
                event.response().setStatusCode(500).end(Json.encode(ResponseErrorBody(ex.message ?: "error")))
            }
    }

    private fun process(cmd: CreatePlayerCmd, event: RoutingContext) {
        validate(cmd)
            .map { valid -> Player.create(valid.name, valid.mail.mail) }
            .map { player -> save(player) }
            .map { ioDto ->
                log.info("mapping")
                ioDto.unsafeRunAsync { either ->
                    log.info("unsafe")
                    when (either) {
                        is Either.Right -> createdResponse(either.b, event)
                        is Either.Left -> errorResponse(event, either.a)
                    }
                }
            }.mapLeft { ex ->
                log.error("<Player>", ex)
                errorResponse(event, ex)
            }
    }

    private fun save(player: Player): IO<PlayerDto> {
        return playerRepository.insert(player.uuid, player)
            .map { p ->
                log.info("created")
                PlayerDto(p.uuid, p.name, p.email)
            }
    }

    private fun <T> createdResponse(t: T, event: RoutingContext) {
        event.response().setStatusCode(200).end(Json.encode(t))
    }

    private fun errorResponse(event: RoutingContext, throwable: Throwable) {
        event.response().setStatusCode(500).end(Json.encode(ResponseErrorBody(throwable.message ?: "error")))
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(PlayerRegister::class.java)
    }
}
