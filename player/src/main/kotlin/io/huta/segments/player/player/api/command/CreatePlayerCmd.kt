package io.huta.segments.player.player.api.command

class CreatePlayerCmd {
    var name: String? = null
    var mail: String? = null
}

data class ValidCreatePlayerCmd(val name: String, val mail: Mail)
data class Mail(val mail: String)
