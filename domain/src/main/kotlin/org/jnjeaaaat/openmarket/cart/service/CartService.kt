package org.jnjeaaaat.openmarket.cart.service

import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_MEMBER
import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.jnjeaaaat.openmarket.cart.repository.CartRepository
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val memberRepository: MemberRepository
) {
    private val log = logger()

    fun createCart(memberId: Long) {
        log.info { "장바구니 생성 memberId: $memberId" }

        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberException(NOT_FOUND_MEMBER) }

        cartRepository.save(Cart.of(member))
    }
}