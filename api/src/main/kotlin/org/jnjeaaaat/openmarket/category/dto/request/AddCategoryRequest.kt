package org.jnjeaaaat.openmarket.category.dto.request

import org.jnjeaaaat.openmarket.category.command.AddCategoryCommand

data class AddCategoryRequest(
    val name: String,
    val parentId: Int
)

fun AddCategoryRequest.toCommand(): AddCategoryCommand {
    return AddCategoryCommand(
        name = name,
        parentId = parentId
    )
}