package org.jnjeaaaat.openmarket.category.service

import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_CATEGORY
import org.jnjeaaaat.openmarket.category.exception.CategoryException
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.stereotype.Component

@Component
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    private val log = logger()

    fun calculateDepth(parentId: Long? = null): Int {
        if (parentId == null) {
            return 1
        }

        val parent = categoryRepository.findById(parentId)
            .orElseThrow { CategoryException(NOT_FOUND_CATEGORY) }

        return (parent.depth ?: 0) + 1
    }
}