package org.jnjeaaaat.openmarket.category.command

import org.jnjeaaaat.openmarket.category.entity.Category

data class AddCategoryCommand(
    val name: String,
    val parentId: Int
)

fun AddCategoryCommand.toEntity(): Category {
    return Category(
        name = name,
        parentId = parentId
    )
}