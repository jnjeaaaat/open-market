package org.jnjeaaaat.openmarket.category.command

import java.time.LocalDateTime

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

data class CategoryTreeResult(
    val id: Long,
    val name: String,
    val children: MutableList<CategoryTreeResult> = mutableListOf(),
    val depth: Int = 0,
    val sortOrder: Int = 0,
    val isVisible: Boolean = true
)