package io.huta.segments.player

import io.huta.segments.player.player.config.PlayerConfig
import io.huta.segments.player.player.PlayerRouter
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class PlayerRoutes(private val router: Router, private val vertx: Vertx) {

    init {
        router.mountSubRouter("/player", PlayerRouter(vertx, PlayerConfig()))
//        router.mountSubRouter("/player/purchase",
//            io.huta.segments.purchase.PlayerPurchaseRouter(vertx, io.huta.segments.purchase.PlayerPurchaseConfig())
//        )
    }
}
