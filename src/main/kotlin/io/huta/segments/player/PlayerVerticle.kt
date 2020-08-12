package io.huta.segments.player

import io.huta.segments.config.ServerProperties
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PlayerVerticle(
    private val serverProperties: ServerProperties
) : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        val httpServer = vertx.createHttpServer()
        val router = Router.router(vertx)

        PlayerRoutes(router, vertx)

        httpServer.requestHandler(router).listen(serverProperties.serverPort)

        log.info("started on port {}", serverProperties.serverPort)
    }

    override fun stop(stopPromise: Promise<Void>?) {
        super.stop(stopPromise)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(PlayerVerticle::class.java)
    }
}