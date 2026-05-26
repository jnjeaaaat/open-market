package org.jnjeaaaat.openmarket.cart.command

import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.jnjeaaaat.openmarket.cart.entity.CartItem
import org.jnjeaaaat.openmarket.product.entity.Product

fun AddCartItemCommand.toEntity(cart: Cart, product: Product): CartItem {
    return CartItem(
        cart = cart,
        product = product,
        quantity = quantity
    )
}

fun CartItem.toResult(): AddCartItemResult {
    return AddCartItemResult(
        id = requireNotNull(id),
        productId = requireNotNull(product.id),
        quantity = quantity,
        isSelected = isSelected
    )
}
