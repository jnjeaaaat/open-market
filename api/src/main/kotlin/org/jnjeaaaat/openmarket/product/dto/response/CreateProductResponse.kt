package org.jnjeaaaat.openmarket.product.dto.response

import org.jnjeaaaat.openmarket.product.command.CreateProductResult

data class CreateProductResponse(
    val id: Long? = null
)

fun CreateProductResult.toResponse(): CreateProductResponse {
    return CreateProductResponse(
        id = id
    )
}
