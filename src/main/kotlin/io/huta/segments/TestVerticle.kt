package io.huta.segments

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject

class TestVerticle(private val result: JsonObject) : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        println("started $result")
    }

    override fun stop(stopPromise: Promise<Void>?) {
        println("stopped")
    }
}