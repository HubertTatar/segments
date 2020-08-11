package io.huta.segments.config

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

fun configRetriever(vertx: Vertx, name: String): ConfigRetriever = ConfigRetriever.create(
        vertx,
        ConfigRetrieverOptions().addStore(configStoreOpts(name))
)

private fun configStoreOpts(fileName: String): ConfigStoreOptions = ConfigStoreOptions()
        .setFormat("json")
        .setType("file")
        .setConfig(JsonObject().put("path", "conf/$fileName.json"))
