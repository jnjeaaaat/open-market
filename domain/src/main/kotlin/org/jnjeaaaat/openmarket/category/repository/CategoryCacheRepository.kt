package org.jnjeaaaat.openmarket.category.repository

import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult

interface CategoryCacheRepository {

    fun getTree(): List<CategoryTreeResult>?
    fun saveTree(categoryTree: List<CategoryTreeResult>)
    fun deleteTree()
}