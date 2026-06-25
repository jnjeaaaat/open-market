package org.jnjeaaaat.openmarket.order.command

data class CreateOrderCommand(
    val cartItemIds: List<Long>,
    val receiverName: String,
    val address: String,
    val zipCode: String,
    val deliveryMessage: String,
)

data class CreateOrderResult(
    val id: Long
)
