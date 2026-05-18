package org.jnjeaaaat.openmarket.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {

    @Value($$"${spring.data.redis.host}")
    private val host: String? = null

    @Value($$"${spring.data.redis.port}")
    private val port = 0

    companion object {
        private const val REDISSON_HOST_PREFIX = "redis://"
    }

    @Bean
    fun redisTemplate(
        connectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, Any> {

        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(connectionFactory)

            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()

            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = StringRedisSerializer()

            afterPropertiesSet()
        }
    }

    @Bean
    fun redisValueOps(
        redisTemplate: RedisTemplate<String, Any>
    ): ValueOperations<String, Any> {
        return redisTemplate.opsForValue()
    }

    @Bean
    fun redissonClient(): RedissonClient {
        var redisson: RedissonClient?
        val config = Config()
        config.useSingleServer().setAddress("$REDISSON_HOST_PREFIX$host:$port")
        redisson = Redisson.create(config)
        return redisson
    }
}