package org.jnjeaaaat.openmarket.product.dto

data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: Long,
    val initStock: Int,
    val categoryId: Long
)

data class CreateProductResponse(
    val id: Long
)