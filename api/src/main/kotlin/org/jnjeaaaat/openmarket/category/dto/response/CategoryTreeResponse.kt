package org.jnjeaaaat.openmarket.category.dto.response

import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult

data class CategoryTreeResponse(
    val id: Long,
    val name: String,
    val children: List<CategoryTreeResult> = emptyList(),
    val depth: Int = 0,
    val sortOrder: Int = 0,
    val isVisible: Boolean = true
)

fun CategoryTreeResult.toResponse(): CategoryTreeResponse {
    return CategoryTreeResponse(
        id = id,
        name = name,
        children = children,
        depth = depth,
        sortOrder = sortOrder,
        isVisible = isVisible
    )
}

fun List<CategoryTreeResult>.toResponse(): List<CategoryTreeResponse> {
    return map { it.toResponse() }
}