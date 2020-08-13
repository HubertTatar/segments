package io.huta.segments.config

import io.vertx.core.json.JsonObject

data class ServerProperties(val serverPort: Int) {
    companion object {
        fun from(result: JsonObject): ServerProperties = ServerProperties(result.getInteger("server-port"))
    }
}
