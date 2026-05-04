package org.jnjeaaaat.openmarket.wallet.event.listener

import org.jnjeaaaat.openmarket.member.event.MemberRegisteredEvent
import org.jnjeaaaat.openmarket.wallet.service.WalletService
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class WalletEventListener(
    private val walletService: WalletService
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleMemberRegisteredEvent(event: MemberRegisteredEvent) {
        walletService.createWallet(event.memberId)
    }

}