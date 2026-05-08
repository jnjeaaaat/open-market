package org.jnjeaaaat.openmarket.product.usecase

import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_CATEGORY
import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_MEMBER
import org.jnjeaaaat.openmarket.category.exception.CategoryException
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.jnjeaaaat.openmarket.common.publish
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.product.command.CreateProductCommand
import org.jnjeaaaat.openmarket.product.command.CreateProductResult
import org.jnjeaaaat.openmarket.product.command.toEntity
import org.jnjeaaaat.openmarket.product.command.toResult
import org.jnjeaaaat.openmarket.product.event.ProductCreatedEvent
import org.jnjeaaaat.openmarket.product.repository.ProductRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateProductUseCase(
    private val memberRepository: MemberRepository,
    private val categoryRepository: CategoryRepository,
    private val publisher: ApplicationEventPublisher,
    private val productRepository: ProductRepository
) {

    @Transactional
    operator fun invoke(command: CreateProductCommand, memberId: Long): CreateProductResult {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberException(NOT_FOUND_MEMBER) }
        val category = categoryRepository.findById(command.categoryId)
            .orElseThrow { CategoryException(NOT_FOUND_CATEGORY) }

        val savedProduct = productRepository.save(
            command.toEntity(member, category)
        )

        publisher.publish(
            ProductCreatedEvent(requireNotNull(savedProduct.id))
        )

        return savedProduct.toResult()
    }
}