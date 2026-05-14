package org.jnjeaaaat.openmarket.category.dto.response

import org.jnjeaaaat.openmarket.category.command.ChildCategory
import org.jnjeaaaat.openmarket.category.command.GetCategoryResult
import java.time.LocalDateTime

data class GetCategoryResponse(
    val id: Long,
    val name: String,
    val depth: Int,
    val sortOrder: Int,
    val children: List<ChildCategoryResponse> = emptyList(),
    val isVisible: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)

data class ChildCategoryResponse(
    val id: Long,
    val name: String,
    val depth: Int,
    val sortOrder: Int,
    val isVisible: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)

fun GetCategoryResult.toResponse(): GetCategoryResponse {
    return GetCategoryResponse(
        id = id,
        name = name,
        depth = depth,
        sortOrder = sortOrder,
        children = children.map { it.toResponse() },
        isVisible = isVisible,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun ChildCategory.toResponse(): ChildCategoryResponse {
    return ChildCategoryResponse(
        id = id,
        name = name,
        depth = depth,
        sortOrder = sortOrder,
        isVisible = isVisible,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}