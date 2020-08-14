package io.huta.segments.player

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class CreatePlayerSpec {

    @Test
    fun test() {
        println("test")
        Assertions.assertEquals(1, 1)
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun before() {
            println("before")
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            println("after")
        }
    }
}
