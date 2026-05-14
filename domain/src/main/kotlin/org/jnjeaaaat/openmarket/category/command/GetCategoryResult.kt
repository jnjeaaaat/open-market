package org.jnjeaaaat.openmarket.category.command

import org.jnjeaaaat.openmarket.category.entity.Category
import java.time.LocalDateTime

data class GetCategoryResult(
    val id: Long,
    val name: String,
    val depth: Int,
    val sortOrder: Int,
    val children: List<ChildCategory> = emptyList(),
    val isVisible: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)

data class ChildCategory(
    val id: Long,
    val name: String,
    val depth: Int,
    val sortOrder: Int,
    val isVisible: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)

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