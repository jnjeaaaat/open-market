package org.jnjeaaaat.openmarket.cart.repository

import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    fun findByMemberId(memberId: Long): Cart?
}