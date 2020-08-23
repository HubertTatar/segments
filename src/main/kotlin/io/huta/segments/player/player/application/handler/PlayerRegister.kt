package io.huta.segments.player.player.application.handler

import arrow.core.Either
import arrow.fx.IO
import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.api.command.CreatePlayerCmd
import io.huta.segments.player.player.api.dto.PlayerDto
import io.huta.segments.player.player.domain.Player
import io.huta.segments.player.player.domain.PlayerRepository
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

class PlayerRegister(private val playerRepository: PlayerRepository) : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        runCatching { Json.decodeValue(event.bodyAsString, CreatePlayerCmd::class.java) }
            .map { cmd -> validate(cmd) }
            .map { player -> save(player) }
            .map { ioDto ->
                log.info("mapping")
                ioDto.unsafeRunAsync { either ->
                    log.info("unsafe")
                    when (either) {
                        is Either.Right -> event.response().setStatusCode(200).end(Json.encode(either.b))
                        is Either.Left -> event.response().setStatusCode(500).end(Json.encode(either.a))
                    }
                }
            }
            .onFailure { ex ->
                log.error("<Player>", ex)
                event.response().setStatusCode(500).end(Json.encode(ResponseErrorBody(ex.message ?: "error")))
            }
    }

    private fun validate(cmd: CreatePlayerCmd): Player {
        log.info("validating")
        val name = cmd.name ?: throw IllegalArgumentException("")
        val mail = cmd.mail ?: throw IllegalArgumentException("")
        return Player(UUID.randomUUID(), name, mail)
    }

    private fun save(player: Player): IO<PlayerDto> {
        return playerRepository.insert(player.uuid, player)
            .map { p ->
                log.info("created")
                PlayerDto(p.uuid, p.name, p.mail)
            }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(PlayerRegister::class.java)
    }
}
