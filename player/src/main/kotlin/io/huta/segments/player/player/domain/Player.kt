package io.huta.segments.player.player.domain

import java.util.UUID

data class Player(val uuid: UUID, val name: String, val email: String) {

    companion object {
        fun create(name: String, email: String): Player =
            Player(UUID.randomUUID(), name, email)

    }
}
