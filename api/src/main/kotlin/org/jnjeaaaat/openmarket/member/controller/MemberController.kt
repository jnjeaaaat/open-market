package org.jnjeaaaat.openmarket.member.controller

import jakarta.validation.Valid
import org.jnjeaaaat.openmarket.member.dto.request.SignUpRequest
import org.jnjeaaaat.openmarket.member.dto.request.toCommand
import org.jnjeaaaat.openmarket.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<Void> {
        val memberId = memberService.signUp(request.toCommand())

        return ResponseEntity
            .created(URI.create("/members/$memberId"))
            .build()
    }

}