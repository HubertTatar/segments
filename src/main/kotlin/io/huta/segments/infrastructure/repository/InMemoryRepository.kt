package io.huta.segments.infrastructure.repository

import io.vertx.core.AsyncResult
import io.vertx.core.Future

abstract class InMemoryRepository<K, V>(
    private val map: MutableMap<K, V>
) {

    fun insert(key: K, value: V): AsyncResult<V> {
        map[key] = value
        return Future.succeededFuture(value)
    }

    fun fetch(key: K): AsyncResult<V?> = Future.succeededFuture(map[key])

    fun delete(key: K): AsyncResult<V?> = Future.succeededFuture(map.remove(key))

    fun update(key: K, value: V): AsyncResult<V?> {
        return Future.succeededFuture(map.replace(key, value))
    }
}
