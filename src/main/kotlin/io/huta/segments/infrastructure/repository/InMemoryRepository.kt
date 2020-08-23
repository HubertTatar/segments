package io.huta.segments.infrastructure.repository

import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import io.vertx.core.Handler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class InMemoryRepository<K, V> (
    private val inMemoryHandlerRepository: InMemoryHandlerRepository<K, V>
) {

    fun insert(key: K, value: V): IO<V> = IO.async { res ->
        inMemoryHandlerRepository.insert(key, value, Handler { async ->
            log.info("insert $key")
            if (async.succeeded()) {
                res(Right(async.result()))
            } else {
                if (async.cause() != null) {
                    res(Left(async.cause()))
                } else {
                    res(Left(RuntimeException("No exception")))
                }
            }
        })
    }

    fun fetch(key: K): IO<V?> = IO.async { res ->
        inMemoryHandlerRepository.fetch(key, Handler { async ->
            log.info("fetch $key")
            if (async.succeeded()) {
                res(Right(async.result()))
            } else {
                if (async.cause() != null) {
                    res(Left(async.cause()))
                } else {
                    res(Left(RuntimeException("No exception")))
                }
            }
        })
    }

    fun delete(key: K): IO<V?> = IO.async { res ->
        inMemoryHandlerRepository.delete(key, Handler { async ->
            log.info("delete $key")
            if (async.succeeded()) {
                res(Right(async.result()))
            } else {
                if (async.cause() != null) {
                    res(Left(async.cause()))
                } else {
                    res(Left(RuntimeException("No exception")))
                }
            }
        })
    }

    fun update(key: K, value: V): IO<V?>  = IO.async { res ->
        inMemoryHandlerRepository.update(key, value, Handler { async ->
            log.info("update $key")
            if (async.succeeded()) {
                res(Right(async.result()))
            } else {
                if (async.cause() != null) {
                    res(Left(async.cause()))
                } else {
                    res(Left(RuntimeException("No exception")))
                }
            }
        })

    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(InMemoryRepository::class.java)
    }
}