package org.jnjeaaaat.openmarket.wallet.usecase.component

import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.util.logger
import org.jnjeaaaat.openmarket.wallet.exception.WalletException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class WalletChargeLimitValidator(
    private val redisValueOps: ValueOperations<String, Any>,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    private val log = logger()

    companion object {
        private const val DAILY_CHARGING_LIMIT = 1_000_000
        private const val CHARGE_LIMIT_KEY = "wallet:charge:limit:"
    }

    operator fun invoke(amount: Long, memberId: Long) {
        val key = "$CHARGE_LIMIT_KEY$memberId"

        // 자정까지 남은 시간
        val remainSeconds = getRemainTimeUntilMidnight()

        // 증가시킨 결과값
        val increased = redisValueOps.increment(key, amount)
            ?: throw WalletException(ErrorCode.INTERNAL_ERROR)

        if (increased == amount) {
            redisTemplate.expire(
                key,
                Duration.ofSeconds(remainSeconds)
            )
        }

        // 한도 초과
        if (increased > DAILY_CHARGING_LIMIT) {
            log.error { "Charge Limit Exceeded Member = $memberId" }
            // 롤백
            redisValueOps.decrement(key, amount)

            throw WalletException(ErrorCode.CHARGE_LIMIT_EXCEEDED)
        }
    }

    private fun getRemainTimeUntilMidnight(): Long {
        return Duration.between(
            LocalDateTime.now(),
            LocalDate.now()
                .plusDays(1)
                .atStartOfDay()
        ).seconds
    }
}