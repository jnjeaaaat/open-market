package org.jnjeaaaat.openmarket.category.usecase


import org.jnjeaaaat.openmarket.category.command.AddCategoryCommand
import org.jnjeaaaat.openmarket.category.command.AddCategoryResult
import org.jnjeaaaat.openmarket.category.command.toAddResult
import org.jnjeaaaat.openmarket.category.command.toEntity
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.jnjeaaaat.openmarket.category.service.CategoryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class AddCategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val categoryService: CategoryService
) {

    @Transactional
    operator fun invoke(command: AddCategoryCommand): AddCategoryResult {
        val depth = categoryService.calculateDepth(command.parentId)
        val sortOrder = categoryRepository.getNextSortOrder(command.parentId)

        val savedCategory = categoryRepository.save(command.toEntity(depth, sortOrder))

        val parent = command.parentId
            ?.let { categoryRepository.findById(it).getOrNull() }

        return savedCategory.toAddResult(parent)
    }
}