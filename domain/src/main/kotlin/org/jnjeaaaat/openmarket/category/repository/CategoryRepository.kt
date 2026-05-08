package org.jnjeaaaat.openmarket.category.repository

import org.jnjeaaaat.openmarket.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    @Query(
        """
        select coalesce(max(c.sortOrder), 0)
        from Category c
        where c.parentId = :parentId
        """
    )
    fun findMaxSortOrder(parentId: Long?): Int?

    fun getNextSortOrder(parentId: Long?): Int? {
        return (findMaxSortOrder(parentId) ?: 0) + 1
    }

    fun findAllByParentId(parentId: Long?): List<Category>
}