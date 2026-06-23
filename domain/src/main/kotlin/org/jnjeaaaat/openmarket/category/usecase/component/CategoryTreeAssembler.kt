package org.jnjeaaaat.openmarket.category.usecase.component

import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.entity.Category
import kotlin.collections.get

object CategoryTreeAssembler {

    fun assemble(
        categories: List<Category>
    ): List<CategoryTreeResult> {

        val map = categories.associate { // list to map
            requireNotNull(it.id) to CategoryTreeResult(
                id = requireNotNull(it.id),
                name = it.name,
                depth = requireNotNull(it.depth),
                sortOrder = requireNotNull(it.sortOrder),
                isVisible = it.isVisible
            )
        }

        val roots = mutableListOf<CategoryTreeResult>()

        categories.forEach { category ->

            val current = map[category.id]!!

            if (category.parentId == null) {
                roots.add(current)
                return@forEach
            }

            val parent = map[category.parentId]

            parent?.children?.add(current)
        }

        return roots
    }
}