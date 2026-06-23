package org.jnjeaaaat.openmarket.cart.usecase.component

import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.cart.entity.CartItem
import org.jnjeaaaat.openmarket.cart.exception.CartException

object CartItemValidator {

    fun validateOrderableCartItems(cartItems: List<CartItem>) {
        if (cartItems.isEmpty()) {
            throw CartException(ErrorCode.EMPTY_CART)
        }
    }

    fun validateCartItemIdsNotEmpty(cartItemIds: List<Long>) {
        if (cartItemIds.isEmpty()) {
            throw CartException(ErrorCode.LEAST_ONE_CART_ITEM)
        }
    }

    fun validateCartItemsMatchCount(cartItems: List<CartItem>, cartItemIds: List<Long>) {
        if (cartItems.size != cartItemIds.distinct().size) {
            throw CartException(ErrorCode.UNMATCH_CART_ITEM_COUNT)
        }
    }
}