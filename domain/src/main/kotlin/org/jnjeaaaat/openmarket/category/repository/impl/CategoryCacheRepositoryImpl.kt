package org.jnjeaaaat.openmarket.category.repository.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

@Repository
class CategoryCacheRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : CategoryCacheRepository {

    companion object {
        // DTO 구조 변경, 직렬화 형태 변경, 필드 추가/삭제에 따른 버전 변경
        private const val CATEGORY_TREE_KEY = "category:tree:v1"
        private val CATEGORY_TREE_TTL = 1.hours.toJavaDuration()
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
            .setIfAbsent(
                CATEGORY_TREE_KEY,
                json,
                CATEGORY_TREE_TTL
            )
    }
}