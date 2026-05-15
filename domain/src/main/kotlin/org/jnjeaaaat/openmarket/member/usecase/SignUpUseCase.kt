package org.jnjeaaaat.openmarket.member.usecase

import org.jnjeaaaat.openmarket.ErrorCode.ALREADY_EXISTS_EMAIL
import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.jnjeaaaat.openmarket.cart.repository.CartRepository
import org.jnjeaaaat.openmarket.member.command.SignUpCommand
import org.jnjeaaaat.openmarket.member.command.toEntity
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.wallet.entity.Wallet
import org.jnjeaaaat.openmarket.wallet.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpUseCase(
    private val memberRepository: MemberRepository,
    private val cartRepository: CartRepository,
    private val walletRepository: WalletRepository,
) {

    @Transactional
    operator fun invoke(command: SignUpCommand): Long {
        validateEmail(command.email)

        val savedMember = memberRepository.save(command.toEntity())

        cartRepository.save(Cart.of(savedMember))
        walletRepository.save(Wallet.of(savedMember))

        val memberId = requireNotNull(savedMember.id)

        return memberId
    }

    private fun validateEmail(email: String) {
        if (memberRepository.existsByEmail(email)) {
            throw MemberException(ALREADY_EXISTS_EMAIL)
        }
    }
}