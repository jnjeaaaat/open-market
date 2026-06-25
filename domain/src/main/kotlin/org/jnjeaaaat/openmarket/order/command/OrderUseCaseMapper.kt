package org.jnjeaaaat.openmarket.order.command

import org.jnjeaaaat.openmarket.cart.entity.CartItem
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.order.entity.Order
import java.time.LocalDateTime

fun CreateOrderCommand.toEntity(
    cartItems: List<CartItem>,
    buyer: Member
): Order {
    val totalAmount = cartItems.sumOf { item ->
        item.product.price * item.quantity.toLong()
    }

    return Order(
        buyer = buyer,
        totalAmount = totalAmount,
        receiverName = receiverName,
        address = address,
        zipCode = zipCode,
        deliveryMessage = deliveryMessage,
        paymentExpiredAt = LocalDateTime.now().plusMinutes(10L)
    )
}

fun Order.toResult(): CreateOrderResult {
    return CreateOrderResult(
        id = requireNotNull(id)
    )
}