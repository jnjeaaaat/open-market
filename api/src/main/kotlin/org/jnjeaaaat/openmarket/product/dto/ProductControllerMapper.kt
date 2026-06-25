package org.jnjeaaaat.openmarket.product.dto

import org.jnjeaaaat.openmarket.product.command.CreateProductCommand
import org.jnjeaaaat.openmarket.product.command.CreateProductResult

fun CreateProductRequest.toCommand(): CreateProductCommand {
    return CreateProductCommand(
        name = name,
        description = description,
        price = price,
        initStock = initStock,
        categoryId = categoryId
    )
}

fun CreateProductResult.toResponse(): CreateProductResponse {
    return CreateProductResponse(
        id = id
    )
}