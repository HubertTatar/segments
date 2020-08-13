package io.huta.segments.player.player

import io.vertx.core.Vertx
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.impl.RouterImpl

class PlayerRouter(vertx: Vertx, playerConfig: PlayerConfig) : RouterImpl(vertx) {

    init {
        get("/").handler(playerConfig.playerGet())

        route("/").handler(BodyHandler.create())
        post("/").handler(playerConfig.registerPlayer())
    }
}
