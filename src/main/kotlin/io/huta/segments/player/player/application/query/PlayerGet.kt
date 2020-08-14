package io.huta.segments.player.player.application.query

import io.huta.segments.player.player.api.dto.PlayerDto
import io.vertx.core.Handler
import io.vertx.core.http.impl.MimeMapping
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import java.util.UUID

class PlayerGet : Handler<RoutingContext> {

    override fun handle(event: RoutingContext) {
        event.response()
            .putHeader("content-type", MimeMapping.getMimeTypeForExtension("json"))
            .end(Json.encode(PlayerDto(UUID.randomUUID(), "JohnDoe", "mail@mail.com")))
    }
}
