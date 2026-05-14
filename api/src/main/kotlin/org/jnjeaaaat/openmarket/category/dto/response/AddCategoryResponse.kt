package org.jnjeaaaat.openmarket.category.dto.response

import org.jnjeaaaat.openmarket.category.command.AddCategoryResult

data class AddCategoryResponse(
    val id: Long,
    val name: String,
    val depth: Int,
    val sortOrder: Int,
    val parentId: Long? = null,
    val parentName: String? = null
)

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