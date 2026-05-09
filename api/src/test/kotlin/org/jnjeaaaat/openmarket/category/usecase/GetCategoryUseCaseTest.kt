package org.jnjeaaaat.openmarket.category.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.category.command.toChildResult
import org.jnjeaaaat.openmarket.category.exception.CategoryException
import org.jnjeaaaat.openmarket.category.fixture.CategoryFixture.category
import org.jnjeaaaat.openmarket.category.repository.CategoryRepository
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

class GetCategoryUseCaseTest : FunSpec({

    val categoryRepository = mockk<CategoryRepository>()

    val getCategoryUseCase = GetCategoryUseCase(categoryRepository)

    beforeTest {
        clearMocks(categoryRepository)
    }

    test("존재하지 않는 Category 예외 발생") {

        // given
        val categoryId = 1L

        every { categoryRepository.findByIdOrNull(any()) } returns null

        // when
        // then
        shouldThrow<CategoryException> {
            getCategoryUseCase(categoryId)
        }.errorCode shouldBe ErrorCode.NOT_FOUND_CATEGORY

        verify(exactly = 0) {
            categoryRepository.findAllByParentId(
                match { it == categoryId }
            )
        }
    }

    test("정상적으로 Category 조회") {

        // given
        val categoryId = 1L
        val category = category().apply { id = categoryId }
        val children = listOf(
            category(
                parentId = categoryId,
                name = "상의"
            )
                .apply { id = 2L }
                .apply { createdAt = LocalDateTime.now() },
            category(
                parentId = categoryId,
                name = "하의",
                sortOrder = 2
            )
                .apply { id = 3L }
                .apply { createdAt = LocalDateTime.now() }
        ) // Entity
        val childrenResult = children.map { it.toChildResult() }

        every {
            categoryRepository.findByIdOrNull(any())
        } returns category

        every {
            categoryRepository.findAllByParentId(any())
        } returns children

        // when
        val result = getCategoryUseCase(categoryId)

        result.id shouldBe categoryId
        result.name shouldBe "전자기기"
        result.children shouldBe childrenResult
        result.children[0].id shouldBe 2L
        result.children[1].id shouldBe 3L

        verify(exactly = 1) {
            categoryRepository.findByIdOrNull(
                match { it == categoryId }
            )
        }

        verify(exactly = 1) {
            categoryRepository.findAllByParentId(
                match { it == categoryId }
            )
        }
    }
})