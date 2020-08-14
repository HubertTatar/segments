package io.huta.segments.player.player.application.handler

import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.api.dto.PlayerDto
import io.huta.segments.player.player.application.command.CreatePlayerCmd
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
            .map { validate(it) }
            .map { save(it) }
            .map { dto -> event.response().setStatusCode(200).end(Json.encode(dto)) }
            .onFailure { ex ->
                log.error("<Player>", ex)
                event.response().setStatusCode(500).end(Json.encode(ResponseErrorBody(ex.message ?: "error")))
            }
    }

    fun validate(cmd: CreatePlayerCmd): Player {
        val name = cmd.name ?: throw IllegalArgumentException("")
        val mail = cmd.mail ?: throw IllegalArgumentException("")
        return Player(UUID.randomUUID(), name, mail)
    }

    fun save(player: Player): PlayerDto {
        playerRepository.insert(player.uuid, player)
        log.info("created")
        return PlayerDto(player.uuid, player.name, player.mail)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(PlayerRegister::class.java)
    }
}
