package org.jnjeaaaat.openmarket.member.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.jnjeaaaat.openmarket.member.command.SignUpCommand

data class SignUpRequest(
    @NotBlank
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    val email: String,

    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$",
        message = "비밀번호는 8~16자이며, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    val password: String,

    @NotBlank
    val name: String
)

fun SignUpRequest.toCommand(): SignUpCommand {
    return SignUpCommand(
        email = email,
        password = password,
        name = name
    )
}
