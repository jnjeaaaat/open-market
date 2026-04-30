package org.jnjeaaaat.openmarket.category.repository

import org.jnjeaaaat.openmarket.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Int>