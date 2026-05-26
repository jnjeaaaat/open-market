package org.jnjeaaaat.openmarket.cart.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class AddCartItemRequest(
    @NotNull @Min(1)
    val productId: Long,

    @NotNull @Min(1)
    val quantity: Int
)

data class AddCartItemResponse(
    val id: Long,
    val productId: Long,
    val quantity: Int,
    val isSelected: Boolean
)
