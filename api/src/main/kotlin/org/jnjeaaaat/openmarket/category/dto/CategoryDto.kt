package org.jnjeaaaat.openmarket.category.dto

import jakarta.validation.constraints.NotBlank
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import java.time.LocalDateTime

data class AddCategoryRequest(
    @NotBlank
    val name: String,
    val parentId: Long? = null
)

data class AddCategoryResponse(
    val id: Long,
    val name: String,
    val depth: Int,
    val sortOrder: Int,
    val parentId: Long? = null,
    val parentName: String? = null
)

data class CategoryTreeResponse(
    val id: Long,
    val name: String,
    val children: List<CategoryTreeResult> = emptyList(),
    val depth: Int = 0,
    val sortOrder: Int = 0,
    val isVisible: Boolean = true
)

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