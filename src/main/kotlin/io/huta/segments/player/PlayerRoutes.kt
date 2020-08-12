package io.huta.segments.player

import io.huta.segments.player.player.PlayerConfig
import io.huta.segments.player.player.PlayerRouter
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class PlayerRoutes(private val router: Router, private val vertx: Vertx) {

    init {
        router.mountSubRouter("/player", PlayerRouter(vertx, PlayerConfig()))
    }
}