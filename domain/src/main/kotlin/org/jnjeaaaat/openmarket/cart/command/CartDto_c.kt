package org.jnjeaaaat.openmarket.cart.command

data class AddCartItemCommand(
    val productId: Long,
    val quantity: Int
)

data class AddCartItemResult(
    val id: Long,
    val productId: Long,
    val quantity: Int,
    val isSelected: Boolean
)
