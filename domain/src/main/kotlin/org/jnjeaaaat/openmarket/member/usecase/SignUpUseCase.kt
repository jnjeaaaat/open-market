package org.jnjeaaaat.openmarket.member.usecase

import org.jnjeaaaat.openmarket.ErrorCode.ALREADY_EXISTS_EMAIL
import org.jnjeaaaat.openmarket.common.publish
import org.jnjeaaaat.openmarket.member.command.SignUpCommand
import org.jnjeaaaat.openmarket.member.command.toEntity
import org.jnjeaaaat.openmarket.member.event.MemberRegisteredEvent
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpUseCase(
    private val memberRepository: MemberRepository,
    private val publisher: ApplicationEventPublisher
) {

    @Transactional
    operator fun invoke(command: SignUpCommand): Long {
        validateEmail(command.email)

        val savedMember = memberRepository.save(command.toEntity())

        val memberId = requireNotNull(savedMember.id)

        publisher.publish(MemberRegisteredEvent(memberId))

        return memberId
    }

    private fun validateEmail(email: String) {
        if (memberRepository.existsByEmail(email)) {
            throw MemberException(ALREADY_EXISTS_EMAIL)
        }
    }
}