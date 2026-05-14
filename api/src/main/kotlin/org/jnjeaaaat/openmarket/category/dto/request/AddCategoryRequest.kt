package org.jnjeaaaat.openmarket.category.dto.request

import jakarta.validation.constraints.NotBlank
import org.jnjeaaaat.openmarket.category.command.AddCategoryCommand

data class AddCategoryRequest(
    @NotBlank
    val name: String,
    val parentId: Long? = null
)

fun AddCategoryRequest.toCommand(): AddCategoryCommand {
    return AddCategoryCommand(
        name = name,
        parentId = parentId
    )
}