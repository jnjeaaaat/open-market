package org.jnjeaaaat.openmarket.lock.executor

import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.lock.aspect.AopForTransaction
import org.jnjeaaaat.openmarket.lock.exception.LockException
import org.redisson.api.RLock
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class LockExecutor(
    private val aopForTransaction: AopForTransaction
) {

    fun execute(
        lock: RLock,
        waitTime: Long,
        leaseTime: Long,
        action: () -> Any?
    ): Any? {

        try {

            val available = lock.tryLock(
                waitTime,
                leaseTime,
                TimeUnit.SECONDS
            )

            if (!available) {
                throw LockException(ErrorCode.INTERNAL_ERROR)
            }

            return aopForTransaction.proceed {
                action()
            }

        } catch (e: InterruptedException) {

            Thread.currentThread().interrupt()

            throw LockException(ErrorCode.INTERNAL_ERROR)

        } finally {

            unlock(lock)
        }
    }

    private fun unlock(lock: RLock) {

        if (lock.isHeldByCurrentThread) {
            lock.unlock()
        }
    }
}