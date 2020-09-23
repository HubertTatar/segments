package io.huta.segments.player.player.api.validation

import arrow.core.Either
import arrow.core.flatMap
import io.huta.segments.player.player.api.command.CreatePlayerCmd
import io.huta.segments.player.player.api.command.Mail
import io.huta.segments.player.player.api.command.ValidCreatePlayerCmd
import io.huta.segments.player.player.application.exception.EmptyString
import io.huta.segments.player.player.application.exception.InvalidEmail


fun validate(cmd: CreatePlayerCmd): Either<Throwable, ValidCreatePlayerCmd> =
    cmd.name.getString().flatMap { name ->
        cmd.mail.getEmail().map { mail ->
            ValidCreatePlayerCmd(name, mail)
        }
    }

private fun String?.getEmail(): Either<Throwable, Mail> =
    if (!this.isNullOrBlank() && this.contains("@")) {
        Either.right(Mail(this))
    } else {
        Either.left(InvalidEmail())
    }

private fun String?.getString(): Either<Throwable, String> =
    if (!this.isNullOrBlank()) {
        Either.right(this)
    } else {
        Either.left(EmptyString())
    }
