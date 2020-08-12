package io.huta.segments.player

import io.huta.segments.player.player.PlayerConfig
import io.huta.segments.player.player.PlayerRouter
import io.huta.segments.player.purchase.PlayerPurchaseConfig
import io.huta.segments.player.purchase.PlayerPurchaseRouter
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class PlayerRoutes(private val router: Router, private val vertx: Vertx) {

    init {
        router.mountSubRouter("/player", PlayerRouter(vertx, PlayerConfig()))
        router.mountSubRouter("/player/purchase", PlayerPurchaseRouter(vertx, PlayerPurchaseConfig()))
    }
}