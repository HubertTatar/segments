package io.huta.segments.player

import io.huta.segments.IntegrationBaseSpec
import io.huta.segments.player.player.api.command.CreatePlayerCmd
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.junit5.Timeout
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(VertxExtension::class)
class PlayerVerticleTest : IntegrationBaseSpec() {

    @Test // TODO add validation for valida uuid
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    fun getPlayerNoPlayer(ctx: VertxTestContext) {
        client().get(8080, "localhost", "/player/5f9e6971-b596-4822-937f-6bc0b0465a04")
            .`as`(BodyCodec.string())
            .send(
                ctx.succeeding { resp ->
                    ctx.verify {
                        Assertions.assertEquals(resp.statusCode(), 404)
                        ctx.completeNow()
                    }
                }
            )
    }

//    @Test // TODO
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    fun getPlayer(ctx: VertxTestContext) {
        client().get(8080, "localhost", "/player")
            .`as`(BodyCodec.string())
            .send(
                ctx.succeeding { resp ->
                    ctx.verify {
                        Assertions.assertTrue(resp.body().toString().contains("JohnDoe"))
                        ctx.completeNow()
                    }
                }
            )
    }

    @Test
    fun createPlayer(ctx: VertxTestContext) {
        // given
        val cmd = CreatePlayerCmd()
        cmd.mail = "johnDoe@mail.com"
        cmd.name = "JognDoe"

        // when
        client().post(8080, "localhost", "/player")
            .sendJson(cmd) { async ->
                // then
                if (async.succeeded()) {
                    ctx.verify {
                        Assertions.assertEquals(async.result().statusCode(), 200)
                        ctx.completeNow()
                    }
                } else {
                    ctx.failed()
                }
            }
    }
}
