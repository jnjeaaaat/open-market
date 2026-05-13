package org.jnjeaaaat.openmarket.category.usecase

import io.kotest.core.spec.style.FunSpec
import io.mockk.*
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
import org.jnjeaaaat.openmarket.category.repository.CategoryQueryRepository

class GetCategoryTreeUseCaseTest : FunSpec({

    val categoryQueryRepository = mockk<CategoryQueryRepository>()
    val categoryCacheRepository = mockk<CategoryCacheRepository>()

    val getCategoryTreeUseCase = GetCategoryTreeUseCase(
        categoryQueryRepository,
        categoryCacheRepository
    )

    beforeTest {
        clearMocks(categoryQueryRepository)
    }

    test("카테고리 트리 두번 조회시 DB 접근 한번") {

        // given
        val categories = listOf(
            CategoryTreeResult(
                id = 1L,
                name = "컴퓨터"
            )
        )

        every {
            categoryCacheRepository.getTree()
        } returns null andThen categories

        every {
            categoryQueryRepository.findVisibleCategories()
        } returns emptyList()

        every {
            categoryCacheRepository.saveTree(any())
        } just Runs

        // when_1 첫 번째 요청
        getCategoryTreeUseCase()

        // then_1
        verify(exactly = 1) {
            categoryQueryRepository.findVisibleCategories()
        }

        verify(exactly = 1) {
            categoryCacheRepository.saveTree(any())
        }

        // when_2 두 번째 요청
        getCategoryTreeUseCase()

        // then_2
        verify(exactly = 1) {
            categoryQueryRepository.findVisibleCategories()
        }
    }

})