package org.jnjeaaaat.openmarket.category.usecase

import org.jnjeaaaat.openmarket.ErrorCode.ALREADY_EXISTS_CATEGORY
import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_CATEGORY
import org.jnjeaaaat.openmarket.category.command.AddCategoryCommand
import org.jnjeaaaat.openmarket.category.command.AddCategoryResult
import org.jnjeaaaat.openmarket.category.command.toAddResult
import org.jnjeaaaat.openmarket.category.command.toEntity
import org.jnjeaaaat.openmarket.category.exception.CategoryException
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    operator fun invoke(command: AddCategoryCommand): AddCategoryResult {
        validateDuplicateName(command.parentId, command.name)

        val parent = command.parentId
            ?.let {
                categoryRepository.findByIdOrNull(it)
                    ?: throw CategoryException(NOT_FOUND_CATEGORY)
            }

        val depth = (parent?.depth ?: 0) + 1
        val sortOrder = categoryRepository
            .getNextSortOrder(command.parentId)

        val savedCategory = categoryRepository.save(
            command.toEntity(depth, sortOrder)
        )

        return savedCategory.toAddResult(parent)
    }

    private fun validateDuplicateName(parentId: Long? = null, name: String) {
        if (categoryRepository.existsByParentIdAndName(parentId, name)) {
            throw CategoryException(ALREADY_EXISTS_CATEGORY)
        }
    }
}