package org.jnjeaaaat.openmarket.product.command

data class CreateProductCommand(
    val name: String,
    val description: String,
    val price: Long,
    val initStock: Int,
    val categoryId: Long
)

data class CreateProductResult(
    val id: Long
)