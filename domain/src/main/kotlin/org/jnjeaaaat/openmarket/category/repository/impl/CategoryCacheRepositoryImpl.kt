package org.jnjeaaaat.openmarket.category.repository.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.benmanes.caffeine.cache.Cache
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

@Repository
class CategoryCacheRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper,
    private val categoryTreeCache: Cache<String, List<CategoryTreeResult>>
) : CategoryCacheRepository {

    private val log = logger();

    companion object {
        // DTO 구조 변경, 직렬화 형태 변경, 필드 추가/삭제에 따른 버전 변경
        private const val CATEGORY_TREE_KEY = "category:tree:v1"
        private val CATEGORY_TREE_TTL = 1.hours.toJavaDuration()
    }

    override fun getTree(): List<CategoryTreeResult>? {

        // local cache 조회
        categoryTreeCache.getIfPresent(CATEGORY_TREE_KEY)
            ?.let {
                log.info { "local cache hit!" }
                return it
            }

        val json = redisTemplate.opsForValue()
            .get(CATEGORY_TREE_KEY)?.toString()
            ?: return null

        val tree = objectMapper.readValue<List<CategoryTreeResult>>(json)

        // local cache 저장
        categoryTreeCache.put(CATEGORY_TREE_KEY, tree)

        return tree
    }

    override fun saveTree(
        categoryTree: List<CategoryTreeResult>
    ) {

        // local cache 저장
        categoryTreeCache.put(CATEGORY_TREE_KEY, categoryTree)

        val json = objectMapper.writeValueAsString(categoryTree)

        redisTemplate.opsForValue()
            .setIfAbsent(
                CATEGORY_TREE_KEY,
                json,
                CATEGORY_TREE_TTL
            )
    }

    override fun deleteTree() {

        redisTemplate.delete(CATEGORY_TREE_KEY)

        categoryTreeCache.invalidate(CATEGORY_TREE_KEY)
    }
}