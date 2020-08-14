package io.huta.segments

import io.huta.segments.config.ServerProperties
import io.huta.segments.config.configRetriever
import io.huta.segments.player.PlayerVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

open class IntegrationBaseSpec {

    var vertx: Vertx? = null
    var port: Int? = null

    @BeforeAll
    fun setup() {
        val bootstrap = Vertx.vertx()

        val vertxRetriever = configRetriever(bootstrap, "vertx")
        val serverRetriever = configRetriever(bootstrap, "server")
        val deploymentRetriever = configRetriever(bootstrap, "deployment")

        vertxRetriever.getConfig { json ->
            bootstrap.close()
            val vertx = Vertx.vertx(VertxOptions(json.result()))
            serverRetriever.getConfig { server ->
                deploymentRetriever.getConfig { deployment ->
                    val serverProperties = ServerProperties.from(server.result())
                    vertx.deployVerticle(PlayerVerticle(serverProperties), DeploymentOptions(deployment.result()))
                }
            }
        }
    }

    @AfterAll
    fun cleanup() {
        vertx?.close()
    }
}
