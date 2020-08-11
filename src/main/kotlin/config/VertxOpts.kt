package config

import io.vertx.config.ConfigStoreOptions
import io.vertx.core.DeploymentOptions
import io.vertx.core.VertxOptions
import io.vertx.core.json.JsonObject

fun configStoreOpts(fileName: String): ConfigStoreOptions = ConfigStoreOptions()
        .setFormat("json")
        .setType("file")
        .setConfig(JsonObject().put("path", "conf/$fileName.json"))
