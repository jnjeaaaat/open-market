package org.jnjeaaaat.openmarket.cart.command

import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.jnjeaaaat.openmarket.cart.entity.CartItem
import org.jnjeaaaat.openmarket.order.entity.Order
import org.jnjeaaaat.openmarket.order.entity.OrderItem
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

fun CartItem.toOrderItem(order: Order): OrderItem {
    return OrderItem(
        order = order,
        product = product,
        seller = product.seller,
        orderPrice = product.price,
        quantity = quantity
    )
}