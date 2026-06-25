package org.jnjeaaaat.openmarket.lock.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.lock.annotation.DistributedLock
import org.jnjeaaaat.openmarket.lock.annotation.DistributedMultiLock
import org.jnjeaaaat.openmarket.lock.exception.LockException
import org.jnjeaaaat.openmarket.lock.executor.LockExecutor
import org.jnjeaaaat.openmarket.lock.parser.LockKeyParser
import org.redisson.RedissonMultiLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAspect(
    private val redissonClient: RedissonClient,
    private val lockExecutor: LockExecutor,
    private val lockKeyParser: LockKeyParser
) {

    @Around("@annotation(lock)")
    fun lock(
        joinPoint: ProceedingJoinPoint,
        lock: DistributedLock
    ): Any? {

        val key =
            lockKeyParser.parse(
                joinPoint,
                lock.key
            )

        val rLock =
            redissonClient.getLock(key)

        return lockExecutor.execute(
            lock = rLock,
            waitTime = lock.waitTime,
            leaseTime = lock.leaseTime
        ) {
            joinPoint.proceed()
        }
    }

    @Around("@annotation(lock)")
    fun lock(
        joinPoint: ProceedingJoinPoint,
        lock: DistributedMultiLock
    ): Any? {

        val lockKeys = lock.keys
            .flatMap { expression ->
                when (val parsed = lockKeyParser.parseToAny(joinPoint, expression)) {
                    is String -> listOf(parsed)
                    is Collection<*> -> parsed.map { it.toString() }
                    is Array<*> -> parsed.map { it.toString() }
                    else -> throw LockException(ErrorCode.INTERNAL_ERROR)
                }
            }
            .distinct()
            .sorted()

        val locks = lockKeys.map { key ->
            redissonClient.getLock(key)
        }

        val multiLock =
            RedissonMultiLock(
                *locks.toTypedArray()
            )

        return lockExecutor.execute(
            lock = multiLock,
            waitTime = lock.waitTime,
            leaseTime = lock.leaseTime
        ) {
            joinPoint.proceed()
        }
    }
}