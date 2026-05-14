package org.jnjeaaaat.openmarket.category.command

import org.jnjeaaaat.openmarket.category.entity.Category

data class AddCategoryCommand(
    val name: String,
    val parentId: Long? = null
)

data class AddCategoryResult(
    val id: Long,
    val name: String,
    val depth: Int,
    val sortOrder: Int,
    val parentId: Long?,
    val parentName: String?
)

fun AddCategoryCommand.toEntity(depth: Int?, sortOrder: Int): Category {
    return Category(
        name = name,
        parentId = parentId,
        depth = depth,
        sortOrder = sortOrder
    )
}

fun Category.toAddResult(parent: Category?): AddCategoryResult {
    return AddCategoryResult(
        id = requireNotNull(id),
        name = name,
        depth = requireNotNull(depth),
        sortOrder = requireNotNull(sortOrder),
        parentId = parentId,
        parentName = parent?.name
    )
}