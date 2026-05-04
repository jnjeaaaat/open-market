package org.jnjeaaaat.openmarket.cart.event.listener

import org.jnjeaaaat.openmarket.cart.service.CartService
import org.jnjeaaaat.openmarket.member.event.MemberRegisteredEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CartEventListener(
    private val cartService: CartService
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleMemberRegisteredEvent(event: MemberRegisteredEvent) {
        cartService.createCart(event.memberId)
    }
}