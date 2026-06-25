package org.jnjeaaaat.openmarket.cart.dto

import org.jnjeaaaat.openmarket.cart.command.AddCartItemCommand
import org.jnjeaaaat.openmarket.cart.command.AddCartItemResult

fun AddCartItemRequest.toCommand(): AddCartItemCommand {
    return AddCartItemCommand(
        productId = productId,
        quantity = quantity
    )
}

fun AddCartItemResult.toResponse(): AddCartItemResponse {
    return AddCartItemResponse(
        id = id,
        productId = productId,
        quantity = quantity,
        isSelected = isSelected
    )
}