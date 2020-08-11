package io.huta.segments.player

import io.huta.segments.config.ServerProperties
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PlayerVerticle(
    private val serverProperties: ServerProperties
) : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        log.info("started {}", serverProperties)
    }

    override fun stop(stopPromise: Promise<Void>?) {
        super.stop(stopPromise)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(PlayerVerticle::class.java)
    }
}