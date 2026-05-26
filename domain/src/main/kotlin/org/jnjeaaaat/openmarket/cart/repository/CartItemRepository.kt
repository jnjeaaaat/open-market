package org.jnjeaaaat.openmarket.cart.repository

import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.jnjeaaaat.openmarket.cart.entity.CartItem
import org.jnjeaaaat.openmarket.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    fun findByCartAndProduct(cart: Cart, product: Product): CartItem?
}
