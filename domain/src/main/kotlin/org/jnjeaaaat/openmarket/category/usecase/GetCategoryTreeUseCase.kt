package org.jnjeaaaat.openmarket.category.usecase

import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
import org.jnjeaaaat.openmarket.category.repository.CategoryQueryRepository
import org.jnjeaaaat.openmarket.category.util.CategoryTreeAssembler.assemble
import org.springframework.stereotype.Service

@Service
class GetCategoryTreeUseCase(
    private val categoryQueryRepository: CategoryQueryRepository,
    private val categoryCacheRepository: CategoryCacheRepository
) {

    operator fun invoke(): List<CategoryTreeResult> {

        val cachedCategories = categoryCacheRepository.getTree()

        if (cachedCategories != null) {
            return cachedCategories
        }

        val categories = categoryQueryRepository.findVisibleCategories()
        val sortedCategories = categories.sortedBy { it.sortOrder }

        val tree = assemble(sortedCategories)

        categoryCacheRepository.saveTree(tree)

        return tree
    }
}