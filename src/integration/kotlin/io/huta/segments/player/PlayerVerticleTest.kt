package io.huta.segments.player

import io.huta.segments.IntegrationBaseSpec
import io.huta.segments.player.player.application.command.CreatePlayerCmd
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

    @Test
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    fun getPlayer(ctx: VertxTestContext) {
        client().get(8080, "localhost", "/player")
            .`as`(BodyCodec.string())
            .send(ctx.succeeding { resp ->
                ctx.verify {
                    Assertions.assertTrue(resp.body().toString().contains("JohnDoe"))
                    ctx.completeNow()
                }
            })
    }

    @Test
    fun createPlayer(ctx: VertxTestContext) {
        //given
        val cmd = CreatePlayerCmd()
        cmd.mail = "johnDoe@mail.com"
        cmd.name = "JognDoe"

        //when
        client().post(8080, "localhost", "/player")
            .sendJson(cmd) { async ->
        //then
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