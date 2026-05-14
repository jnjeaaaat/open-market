package org.jnjeaaaat.openmarket.category.command

import org.jnjeaaaat.openmarket.category.entity.Category

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

fun Category.toGetResult(children: List<Category> = emptyList()): GetCategoryResult {
    return GetCategoryResult(
        id = requireNotNull(id),
        name = name,
        depth = requireNotNull(depth),
        sortOrder = requireNotNull(sortOrder),
        children = children.map { it.toChildResult() },
        isVisible = isVisible,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Category.toChildResult(): ChildCategory {
    return ChildCategory(
        id = requireNotNull(id),
        name = name,
        depth = requireNotNull(depth),
        sortOrder = requireNotNull(sortOrder),
        isVisible = isVisible,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}