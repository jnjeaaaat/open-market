package org.jnjeaaaat.openmarket.category.repository.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.jnjeaaaat.openmarket.category.entity.Category
import org.jnjeaaaat.openmarket.category.entity.QCategory.category
import org.jnjeaaaat.openmarket.category.repository.CategoryQueryRepository
import org.springframework.stereotype.Repository

@Repository
class CategoryQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CategoryQueryRepository {

    override fun findVisibleCategories(): List<Category> {
        return jpaQueryFactory
            .selectFrom(category)
            .where(category.isVisible.isTrue)
            .orderBy(
                category.parentId.asc(),
                category.sortOrder.asc()
            )
            .fetch()
    }
}