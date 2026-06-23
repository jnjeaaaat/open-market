package org.jnjeaaaat.openmarket.order.dto

import org.jnjeaaaat.openmarket.order.command.CreateOrderCommand
import org.jnjeaaaat.openmarket.order.command.CreateOrderResult

fun CreateOrderRequest.toCommand(): CreateOrderCommand {
    return CreateOrderCommand(
        cartItemIds = cartItemIds,
        receiverName = receiverName,
        address = address,
        zipCode = zipCode,
        deliveryMessage = deliveryMessage
    )
}

fun CreateOrderResult.toResponse(): CreateOrderResponse {
    return CreateOrderResponse(
        id = id
    )
}