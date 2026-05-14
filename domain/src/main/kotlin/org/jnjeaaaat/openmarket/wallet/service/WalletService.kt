package org.jnjeaaaat.openmarket.wallet.service

import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_MEMBER
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.util.logger
import org.jnjeaaaat.openmarket.wallet.entity.Wallet
import org.jnjeaaaat.openmarket.wallet.repository.WalletRepository
import org.springframework.stereotype.Service

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val memberRepository: MemberRepository
) {
    private val log = logger()

    fun createWallet(memberId: Long) {
        log.info { "지갑 생성 memberId: $memberId" }

        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberException(NOT_FOUND_MEMBER) }

        walletRepository.save(Wallet.of(member))
    }
}