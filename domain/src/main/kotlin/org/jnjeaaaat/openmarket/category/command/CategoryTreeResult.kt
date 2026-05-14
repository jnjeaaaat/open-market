package org.jnjeaaaat.openmarket.category.command

data class CategoryTreeResult(
    val id: Long,
    val name: String,
    val children: MutableList<CategoryTreeResult> = mutableListOf(),
    val depth: Int = 0,
    val sortOrder: Int = 0,
    val isVisible: Boolean = true
)