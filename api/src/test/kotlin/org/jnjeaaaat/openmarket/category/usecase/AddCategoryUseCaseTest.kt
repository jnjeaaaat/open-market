package org.jnjeaaaat.openmarket.category.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.category.exception.CategoryException
import org.jnjeaaaat.openmarket.category.fixture.CategoryFixture.addCategoryCommand
import org.jnjeaaaat.openmarket.category.fixture.CategoryFixture.category
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.springframework.data.repository.findByIdOrNull

class AddCategoryUseCaseTest : FunSpec({

    val categoryRepository = mockk<CategoryRepository>()
    val categoryCacheRepository = mockk<CategoryCacheRepository>()

    val addCategoryUseCase = AddCategoryUseCase(categoryRepository, categoryCacheRepository)

    beforeTest {
        clearMocks(categoryRepository)
    }

    test("중복 카테고리 예외 발생") {

        // given
        every {
            categoryRepository.existsByParentIdAndName(any(), any())
        } returns true

        // when
        // then
        shouldThrow<CategoryException> {
            addCategoryUseCase(addCategoryCommand())
        }.errorCode shouldBe ErrorCode.ALREADY_EXISTS_CATEGORY

        verify(exactly = 1) {
            categoryRepository.existsByParentIdAndName(any(), any())
        }

        verify(exactly = 0) {
            categoryRepository.save(any())
        }
    }

    test("null 이 아닌 부모카테고리가 없으면 예외 발생") {

        // given
        val command = addCategoryCommand(
            parentId = 100L
        )

        every {
            categoryRepository.existsByParentIdAndName(any(), any())
        } returns false

        every {
            categoryRepository.findByIdOrNull(command.parentId)
        } returns null

        // when
        // then
        shouldThrow<CategoryException> {
            addCategoryUseCase(command)
        }.errorCode shouldBe ErrorCode.NOT_FOUND_CATEGORY

        verify(exactly = 1) {
            categoryRepository.existsByParentIdAndName(any(), any())
        }

        verify(exactly = 0) {
            categoryRepository.save(
                match {
                    it.parentId == command.parentId &&
                            it.name == command.name
                }
            )
        }
    }

    test("정상적인 값이 주어지고 Category 저장") {

        // given
        val command = addCategoryCommand(
            name = "노트북",
            parentId = 1L
        )
        val parentCategory = category().apply { id = 1L }
        val savedCategory = category(
            name = command.name,
            parentId = command.parentId,
            depth = parentCategory.depth!! + 1
        ).apply { id = 2L }

        every {
            categoryRepository.existsByParentIdAndName(any(), any())
        } returns false

        every {
            categoryRepository.findByIdOrNull(any())
        } returns parentCategory

        every {
            categoryRepository.getNextSortOrder(any())
        } returns 1

        // when
        every {
            categoryRepository.save(any())
        } returns savedCategory

        every {
            categoryCacheRepository.deleteTree()
        } just runs

        val result = addCategoryUseCase(command)

        // then
        result.id shouldBe 2L
        result.name shouldBe command.name
        result.depth shouldBe parentCategory.depth!! + 1
        result.sortOrder shouldBe 1
        result.parentId shouldBe command.parentId
        result.parentName shouldBe parentCategory.name

        verify(exactly = 1) {
            categoryRepository.existsByParentIdAndName(
                command.parentId,
                command.name
            )
        }

        verify(exactly = 1) {
            categoryRepository.findByIdOrNull(any())
        }

        verify(exactly = 1) {
            categoryRepository.save(any())
        }
    }
})