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
        categoryCacheRepository.getTree()
            ?.let { return it }

        // 없으면 db 조회(queryDsl)
        val tree = assemble(
            categoryQueryRepository.findVisibleCategories()
                .sortedBy { it.sortOrder }
        )

        // redis 저장
        categoryCacheRepository.saveTree(tree)

        return tree
    }
}