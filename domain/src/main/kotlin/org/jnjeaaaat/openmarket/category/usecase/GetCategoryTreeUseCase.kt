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

        // redis 캐시 확인
        val cachedCategories = categoryCacheRepository.getTree() // redis 캐시 확인

        if (cachedCategories != null) {
            return cachedCategories
        }

        // 없으면 db 조회(queryDsl)
        val categories = categoryQueryRepository.findVisibleCategories()
        val sortedCategories = categories.sortedBy { it.sortOrder }

        // tree 조립
        val tree = assemble(sortedCategories)

        // redis 저장
        categoryCacheRepository.saveTree(tree)

        return tree
    }
}