package org.jnjeaaaat.openmarket.member.command

import org.jnjeaaaat.openmarket.member.entity.Member

data class SignUpCommand(
    val email: String,
    val password: String,
    val name: String
)

fun SignUpCommand.toEntity(): Member {
    return Member(
        email = email,
        password = password,
        name = name
    )
}