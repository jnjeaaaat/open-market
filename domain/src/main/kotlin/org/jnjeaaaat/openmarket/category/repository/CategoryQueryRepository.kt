package org.jnjeaaaat.openmarket.category.repository

import org.jnjeaaaat.openmarket.category.entity.Category

interface CategoryQueryRepository {

    fun findVisibleCategories(): List<Category>
}