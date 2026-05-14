package org.jnjeaaaat.openmarket.category.dto

import org.jnjeaaaat.openmarket.category.command.AddCategoryCommand
import org.jnjeaaaat.openmarket.category.command.AddCategoryResult
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.command.ChildCategory
import org.jnjeaaaat.openmarket.category.command.GetCategoryResult

fun AddCategoryRequest.toCommand(): AddCategoryCommand {
    return AddCategoryCommand(
        name = name,
        parentId = parentId
    )
}

fun AddCategoryResult.toResponse(): AddCategoryResponse {
    return AddCategoryResponse(
        id = id,
        name = name,
        depth = depth,
        sortOrder = sortOrder,
        parentId = parentId,
        parentName = parentName
    )
}

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