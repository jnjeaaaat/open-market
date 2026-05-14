package org.jnjeaaaat.openmarket.member.command

import org.jnjeaaaat.openmarket.member.entity.Member

fun SignUpCommand.toEntity(): Member {
    return Member(
        email = email,
        password = password,
        name = name
    )
}