package org.jnjeaaaat.openmarket.category.usecase

import org.jnjeaaaat.openmarket.category.command.AddCategoryCommand
import org.jnjeaaaat.openmarket.category.command.toEntity
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    operator fun invoke(command: AddCategoryCommand): Int {
        val savedCategory = categoryRepository.save(command.toEntity())
        return requireNotNull(savedCategory.id)
    }
}