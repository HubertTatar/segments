package io.huta.segments.player

import io.huta.segments.config.ServerProperties
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.junit5.Timeout
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(VertxExtension::class)
open class PlayerVerticleTest {

    var vertx: Vertx? = null

    @BeforeAll
    fun setup(ctx: VertxTestContext) {
        vertx = Vertx.vertx()
        vertx?.deployVerticle(PlayerVerticle(ServerProperties(8080)), ctx.completing())
    }

    @AfterAll
    fun clean(ctx: VertxTestContext) {
        vertx?.close(ctx.completing())
    }

    @Test
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    fun testGetPlayer(ctx: VertxTestContext) {
        val client: WebClient = WebClient.create(vertx)
        client.get(8080, "localhost", "/player")
            .`as`(BodyCodec.string())
            .send(ctx.succeeding { resp ->
                ctx.verify {
                    Assertions.assertTrue(resp.toString().contains("JohnDoe"))
                    ctx.completeNow()
                }
            })
    }
}
