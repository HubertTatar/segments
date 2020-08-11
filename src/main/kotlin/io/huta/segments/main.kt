package io.huta.segments

import io.huta.segments.config.configRetriever
import io.netty.util.internal.logging.InternalLoggerFactory
import io.netty.util.internal.logging.Slf4JLoggerFactory
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main() {

    System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory")
    InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE)

    val bootstrap = Vertx.vertx()

    val vertxRetriever = configRetriever(bootstrap, "vertx")
    val serverRetriever = configRetriever(bootstrap, "server")
    val deploymentRetriever = configRetriever(bootstrap, "deployment")

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