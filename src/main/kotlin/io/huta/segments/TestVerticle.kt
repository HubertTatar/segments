package io.huta.segments

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TestVerticle(private val result: JsonObject) : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        log.info("started {}", result)
    }

    override fun stop(stopPromise: Promise<Void>?) {
        log.info("stopped")
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(TestVerticle::class.java)
    }
}