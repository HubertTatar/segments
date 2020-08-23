package io.huta.segments.infrastructure.repository

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler

class InMemoryHandlerRepository<K, V>(
    private val map: MutableMap<K, V>
) {

    fun insert(key: K, value: V, handler: Handler<AsyncResult<V>>) {
        map[key] = value
        handler.handle(Future.succeededFuture(value))
    }

    fun fetch(key: K, handler: Handler<AsyncResult<V?>>) {
        handler.handle(Future.succeededFuture(map[key]))
    }

    fun delete(key: K, handler: Handler<AsyncResult<V?>>) {
        handler.handle(Future.succeededFuture(map.remove(key)))
    }

    fun update(key: K, value: V, handler: Handler<AsyncResult<V?>>) {
        handler.handle(Future.succeededFuture(map.replace(key, value)))
    }
}
