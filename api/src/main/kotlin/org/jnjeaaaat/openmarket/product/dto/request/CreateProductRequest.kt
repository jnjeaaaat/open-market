package org.jnjeaaaat.openmarket.product.dto.request

import org.jnjeaaaat.openmarket.product.command.CreateProductCommand

data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: Long,
    val initStock: Int,
    val categoryId: Long
)

fun CreateProductRequest.toCommand(): CreateProductCommand {
    return CreateProductCommand(
        name = name,
        description = description,
        price = price,
        initStock = initStock,
        categoryId = categoryId
    )
}