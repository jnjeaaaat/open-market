package org.jnjeaaaat.openmarket.category.usecase

import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.category.command.GetCategoryResult
import org.jnjeaaaat.openmarket.category.command.toGetResult
import org.jnjeaaaat.openmarket.category.exception.CategoryException
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GetCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    private val log = logger()

    operator fun invoke(categoryId: Long): GetCategoryResult {
        val category = categoryRepository.findByIdOrNull(categoryId)
            ?: throw CategoryException(ErrorCode.NOT_FOUND_CATEGORY)

        val children = categoryRepository.findAllByParentId(categoryId)

        return category.toGetResult(children)
    }
}