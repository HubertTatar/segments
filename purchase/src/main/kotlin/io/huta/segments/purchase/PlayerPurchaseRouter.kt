package io.huta.segments.purchase

import io.vertx.core.Vertx
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.impl.RouterImpl

class PlayerPurchaseRouter(
    vertx: Vertx,
    playerPurchaseConfig: PlayerPurchaseConfig
) : RouterImpl(vertx) {

    init {
        route("/").handler(BodyHandler.create())
        post("/").handler(playerPurchaseConfig.playerPurchasePost())
    }
}