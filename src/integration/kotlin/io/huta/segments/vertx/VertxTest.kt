package io.huta.segments.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
class VertxTest {

    @Test
    fun testServer() {
        val ctx = VertxTestContext()
        val vertx = Vertx.vertx()
        vertx.createHttpServer()
            .requestHandler { req -> req.response().end() }
            .listen(16969, ctx.completing())

        Assertions.assertTrue(ctx.awaitCompletion(5, TimeUnit.SECONDS))
        if (ctx.failed()) {
            throw ctx.causeOfFailure()
        }
    }

    @Test
    fun testRequest() {
        val ctx = VertxTestContext()
        val vertx = Vertx.vertx()
        vertx.createHttpServer()
            .requestHandler { req -> req.response().end("Plop") }
            .listen(16968, ctx.completing())

        Assertions.assertTrue(ctx.awaitCompletion(5, TimeUnit.SECONDS))
        if (ctx.failed()) {
            throw ctx.causeOfFailure()
        }

        WebClient.create(vertx).get(16968, "localhost", "/")
            .`as`(BodyCodec.string())
            .send(
                ctx.succeeding { resp ->
                    ctx.verify {
                        Assertions.assertTrue(resp.body() == "Plop")
                        ctx.completeNow()
                    }
                }
            )
    }

    @Test
    fun testDeployment() {
        val ctx = VertxTestContext()
        val vertx = Vertx.vertx()
        vertx.deployVerticle(
            EmptyVerticle(),
            ctx.succeeding { id ->
                println(id)
                ctx.completeNow()
            }
        )
        Assertions.assertTrue(ctx.awaitCompletion(20, TimeUnit.SECONDS))
        if (ctx.failed()) {
            throw ctx.causeOfFailure()
        }
    }
}

class EmptyVerticle : AbstractVerticle()
