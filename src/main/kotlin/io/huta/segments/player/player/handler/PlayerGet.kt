package io.huta.segments.player.player.handler

import io.huta.segments.player.player.domain.model.Player
import io.vertx.core.Handler
import io.vertx.core.http.impl.MimeMapping
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import java.util.UUID

class PlayerGet : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        event.response()
            .putHeader("content-type", MimeMapping.getMimeTypeForExtension("json"))
            .end(Json.encode(Player(UUID.randomUUID())))
    }
}
