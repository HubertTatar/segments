package io.huta.segments

import config.configStoreOpts
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main() {
    val bootstrap = Vertx.vertx()

    val vertxRetriever = ConfigRetriever.create(
        bootstrap,
        ConfigRetrieverOptions().addStore(configStoreOpts("vertx"))
    )

    val serverRetriever = ConfigRetriever.create(
        bootstrap,
        ConfigRetrieverOptions().addStore(configStoreOpts("server"))
    )

    val deploymentRetriever = ConfigRetriever.create(
        bootstrap,
        ConfigRetrieverOptions().addStore(configStoreOpts("deployment"))
    )

    vertxRetriever.getConfig { json ->
        bootstrap.close()
        val vertx = Vertx.vertx(VertxOptions(json.result()))
        serverRetriever.getConfig { server ->
            deploymentRetriever.getConfig { deployment ->
                vertx.deployVerticle(TestVerticle(server.result()), DeploymentOptions(deployment.result()))
            }
        }
    }
}