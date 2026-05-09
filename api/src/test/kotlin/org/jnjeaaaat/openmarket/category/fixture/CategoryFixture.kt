package org.jnjeaaaat.openmarket.category.fixture

import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import org.jnjeaaaat.openmarket.category.command.AddCategoryCommand
import org.jnjeaaaat.openmarket.category.command.AddCategoryResult
import org.jnjeaaaat.openmarket.category.command.ChildCategory
import org.jnjeaaaat.openmarket.category.command.GetCategoryResult
import org.jnjeaaaat.openmarket.category.dto.request.AddCategoryRequest
import org.jnjeaaaat.openmarket.category.entity.Category
import org.jnjeaaaat.openmarket.support.FixtureMonkeyConfig.fixtureMonkey
import java.time.LocalDateTime

object CategoryFixture {

    fun addCategoryRequest(
        name: String = "전자기기",
        parentId: Long? = null
    ) = AddCategoryRequest(
        name = name,
        parentId = parentId
    )

    fun addCategoryCommand(
        name: String = "전자기기",
        parentId: Long? = null
    ) = AddCategoryCommand(
        name = name,
        parentId = parentId
    )

    fun addCategoryResult(
        id: Long = 1L,
        name: String = "전자기기",
        depth: Int = 1,
        sortOrder: Int = 1,
        parentId: Long? = null,
        parentName: String? = null
    ) = AddCategoryResult(
        id = id,
        name = name,
        depth = depth,
        sortOrder = sortOrder,
        parentId = parentId,
        parentName = parentName
    )

    fun getCategoryResult(
        id: Long = 1L,
        name: String = "옷",
        depth: Int = 1,
        sortOrder: Int = 1,
        children: List<ChildCategory> = emptyList(),
        isVisible: Boolean = true,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime? = null
    ) = GetCategoryResult(
        id = id,
        name = name,
        depth = depth,
        sortOrder = sortOrder,
        children = children,
        isVisible = isVisible,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun childCategory(
        id: Long = 2L,
        name: String = "상의",
        depth: Int = 2,
        sortOrder: Int = 1,
        isVisible: Boolean = true,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime? = null
    ) = ChildCategory(
        id = id,
        name = name,
        depth = depth,
        sortOrder = sortOrder,
        isVisible = isVisible,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun category(
        name: String = "전자기기",
        parentId: Long? = null,
        depth: Int = 1,
        sortOrder: Int = 1,
        isVisible: Boolean = true
    ): Category {
        return fixtureMonkey.giveMeKotlinBuilder<Category>()
            .set(Category::name, name)
            .set(Category::parentId, parentId)
            .set(Category::depth, depth)
            .set(Category::sortOrder, sortOrder)
            .set(Category::isVisible, isVisible)
            .sample()
            .apply {
                createdAt = LocalDateTime.now()
            }
    }
}