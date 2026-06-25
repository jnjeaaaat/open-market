package org.jnjeaaaat.openmarket.member.dto

import org.jnjeaaaat.openmarket.member.command.SignUpCommand

fun SignUpRequest.toCommand(): SignUpCommand {
    return SignUpCommand(
        email = email,
        password = password,
        name = name
    )
}