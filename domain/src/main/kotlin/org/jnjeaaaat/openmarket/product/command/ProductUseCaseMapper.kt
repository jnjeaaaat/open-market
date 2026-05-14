package org.jnjeaaaat.openmarket.product.command

import org.jnjeaaaat.openmarket.category.entity.Category
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.product.entity.Product

fun CreateProductCommand.toEntity(member: Member, category: Category): Product {
    return Product(
        name = name,
        seller = member,
        category = category,
        description = description,
        price = price,
        stock = initStock
    )
}

fun Product.toResult(): CreateProductResult {
    return CreateProductResult(
        id = id
    )
}