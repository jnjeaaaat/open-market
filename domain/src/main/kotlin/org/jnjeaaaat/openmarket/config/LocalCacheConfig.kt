package org.jnjeaaaat.openmarket.config

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class LocalCacheConfig {

    @Bean
    fun categoryTreeCache(): Cache<String, List<CategoryTreeResult>> {
        return Caffeine.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build()
    }
}