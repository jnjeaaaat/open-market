package org.jnjeaaaat.openmarket.order.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class CreateOrderRequest(
    @NotEmpty
    val cartItemIds: List<Long>,

    @NotBlank
    val receiverName: String,

    @NotBlank
    val address: String,

    @NotBlank
    val zipCode: String,

    @NotBlank
    val deliveryMessage: String,
)

data class CreateOrderResponse(
    val id: Long
)