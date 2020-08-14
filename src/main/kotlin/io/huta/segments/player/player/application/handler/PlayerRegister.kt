package io.huta.segments.player.player.application.handler

import io.huta.segments.infrastructure.web.ResponseErrorBody
import io.huta.segments.player.player.application.command.CreatePlayerCmd
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PlayerRegister : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        runCatching { Json.decodeValue(event.bodyAsString, CreatePlayerCmd::class.java) }
            .map { validate(it) }
            .map { save(it) }
            .map { event.response().setStatusCode(200).end() }
            .onFailure { ex ->
                log.error("<Player>", ex)
                event.response().setStatusCode(500).end(Json.encode(ResponseErrorBody(ex.message ?: "error")))
            }
    }

    fun validate(cmd: CreatePlayerCmd) = cmd

    fun save(cmd: CreatePlayerCmd): CreatePlayerCmd {
        log.info("created")
        return cmd
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(PlayerRegister::class.java)
    }
}
