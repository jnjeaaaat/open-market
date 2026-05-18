package org.jnjeaaaat.openmarket.lock.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.jnjeaaaat.openmarket.lock.annotation.DistributedLock
import org.jnjeaaaat.openmarket.lock.annotation.DistributedMultiLock
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

        val locks = lock.keys
            .map {
                val key =
                    lockKeyParser.parse(
                        joinPoint,
                        it
                    )

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