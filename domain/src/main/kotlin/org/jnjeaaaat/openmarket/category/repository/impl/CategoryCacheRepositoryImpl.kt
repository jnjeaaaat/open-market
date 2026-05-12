package org.jnjeaaaat.openmarket.category.repository.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class CategoryCacheRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : CategoryCacheRepository {

    companion object {
        private const val CATEGORY_TREE_KEY = "category:tree"
    }

    override fun getTree(): List<CategoryTreeResult>? {

        val json = redisTemplate.opsForValue()
            .get(CATEGORY_TREE_KEY)
            ?: return null

        return objectMapper.readValue<List<CategoryTreeResult>>(json)
    }

    override fun saveTree(
        categoryTree: List<CategoryTreeResult>
    ) {

        val json = objectMapper.writeValueAsString(categoryTree)

        redisTemplate.opsForValue()
            .set(
                CATEGORY_TREE_KEY,
                json
            )
    }
}