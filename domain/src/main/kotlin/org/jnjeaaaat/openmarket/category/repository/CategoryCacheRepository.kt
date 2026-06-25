package org.jnjeaaaat.openmarket.category.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.benmanes.caffeine.cache.Cache
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

@Repository
class CategoryCacheRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper,
    private val categoryTreeCache: Cache<String, List<CategoryTreeResult>>
) {

    private val log = logger()

    companion object {
        // DTO 구조 변경, 직렬화 형태 변경, 필드 추가/삭제에 따른 버전 변경
        private const val CATEGORY_TREE_KEY = "category:tree:v1"
        private val CATEGORY_TREE_TTL = 1.hours.toJavaDuration()
    }

    fun getTree(): List<CategoryTreeResult>? {
        categoryTreeCache.getIfPresent(CATEGORY_TREE_KEY)
            ?.let {
                log.info { "local cache hit!" }
                return it
            }

        val json = getRedisTree()
            ?: return null

        return deserializeTree(json)
            ?.also { categoryTreeCache.put(CATEGORY_TREE_KEY, it) }
    }

    fun saveTree(
        categoryTree: List<CategoryTreeResult>
    ) {
        categoryTreeCache.put(CATEGORY_TREE_KEY, categoryTree)

        saveRedisTree(categoryTree)
    }

    fun deleteTree() {
        deleteRedisTree()

        // Redis 삭제 실패와 관계없이 현재 인스턴스의 stale cache는 제거한다.
        categoryTreeCache.invalidate(CATEGORY_TREE_KEY)
    }

    private fun getRedisTree(): String? {
        return runCatching {
            redisTemplate.opsForValue()
                .get(CATEGORY_TREE_KEY)
                ?.toString()
        }.onFailure {
            log.warn(it) { "카테고리 트리 Redis 조회 실패" }
        }.getOrNull()
    }

    private fun deserializeTree(json: String): List<CategoryTreeResult>? {
        return runCatching {
            objectMapper.readValue<List<CategoryTreeResult>>(json)
        }.onFailure {
            log.warn(it) { "Local Cache tree 역직렬화 실패" }
            categoryTreeCache.invalidate(CATEGORY_TREE_KEY)
        }.getOrNull()
    }

    private fun saveRedisTree(categoryTree: List<CategoryTreeResult>) {
        runCatching {
            val json = objectMapper.writeValueAsString(categoryTree)

            redisTemplate.opsForValue()
                .set(
                    CATEGORY_TREE_KEY,
                    json,
                    CATEGORY_TREE_TTL
                )
        }.onFailure {
            log.warn(it) { "카테고리 트리 Redis 저장 실패" }
        }
    }

    private fun deleteRedisTree() {
        runCatching {
            redisTemplate.delete(CATEGORY_TREE_KEY)
        }.onFailure {
            log.warn(it) { "카테고리 트리 Redis 저장 실패" }
        }
    }
}