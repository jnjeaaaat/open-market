package org.jnjeaaaat.openmarket.member.command

data class SignUpCommand(
    val email: String,
    val password: String,
    val name: String
)