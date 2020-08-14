package io.huta.segments

import io.huta.segments.config.ServerProperties
import io.huta.segments.player.PlayerVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
open class IntegrationBaseSpec {

    var vertx: Vertx? = null

    @BeforeAll
    fun setup() {
        vertx = Vertx.vertx()
        vertx?.deployVerticle(PlayerVerticle(ServerProperties(8080)))
    }

    @AfterAll
    fun clean() {
        vertx?.close()
    }

    fun client(): WebClient = WebClient.create(vertx)
}
