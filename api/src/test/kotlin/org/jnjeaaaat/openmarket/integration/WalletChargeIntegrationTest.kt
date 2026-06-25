package org.jnjeaaaat.openmarket.integration

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.jnjeaaaat.openmarket.Application
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.member.fixture.MemberFixture.member
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.wallet.command.ChargeCommand
import org.jnjeaaaat.openmarket.wallet.entity.Wallet
import org.jnjeaaaat.openmarket.wallet.exception.WalletException
import org.jnjeaaaat.openmarket.wallet.repository.WalletRepository
import org.jnjeaaaat.openmarket.wallet.usecase.WalletChargeUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import kotlin.math.ceil
import kotlin.system.measureNanoTime

@ActiveProfiles("test")
@SpringBootTest(classes = [Application::class])
@ImportTestcontainers
class WalletChargeIntegrationTest : BehaviorSpec() {

    companion object {

        @Container
        @JvmStatic
        val redisContainer = GenericContainer<Nothing>(
            DockerImageName.parse("redis:7-alpine")
        ).apply {
            withExposedPorts(6379)
        }

        @JvmStatic
        @DynamicPropertySource
        fun redisProperties(
            registry: DynamicPropertyRegistry
        ) {
            registry.add("spring.data.redis.host") {
                redisContainer.host
            }

            registry.add("spring.data.redis.port") {
                redisContainer.getMappedPort(6379)
            }
        }
    }

    @Autowired lateinit var memberRepository: MemberRepository
    @Autowired lateinit var walletChargeUseCase: WalletChargeUseCase
    @Autowired lateinit var walletRepository: WalletRepository
    @Autowired lateinit var redisTemplate: RedisTemplate<String, Any>

    override fun extensions() = listOf(SpringExtension)

    init {
        beforeContainer {
            redisTemplate.connectionFactory
                ?.connection
                ?.serverCommands()
                ?.flushAll()
        }

        Given("지갑 충전 조회 성능을 측정할 때") {
            val amount = 1L
            val command = ChargeCommand(amount = amount)
            val warmUpCount = 20
            val measurementCount = 100
            val scenario = System.getProperty("wallet.charge.mapping")
                ?: System.getenv("WALLET_CHARGE_MAPPING")
                ?: "current"

            val savedMember = memberRepository.save(
                member(email = "wallet-charge-${System.nanoTime()}@test.com")
            )
            walletRepository.save(createWallet(savedMember))
            val memberId = requireNotNull(savedMember.id)
            val chargeLimitKey = "wallet:charge:limit:$memberId"

            When("충전 요청을 warm-up 이후 100회 순차 실행하면") {
                redisTemplate.delete(chargeLimitKey)

                repeat(warmUpCount) {
                    walletChargeUseCase(command, memberId)
                }

                val durations = List(measurementCount) {
                    measureNanoTime {
                        walletChargeUseCase(command, memberId)
                    }
                }

                Then("p95/p99 시간을 확인할 수 있다") {
                    val result = ChargeLatencyResult.from(durations)

                    println(
                        """

                        [WalletChargeIntegrationTest]
                        scenario=$scenario
                        count=${result.count}
                        avg=${result.avgMs}ms
                        p50=${result.p50Ms}ms
                        p95=${result.p95Ms}ms
                        p99=${result.p99Ms}ms
                        min=${result.minMs}ms
                        max=${result.maxMs}ms
                        """.trimIndent()
                    )

                    val updatedWallet = walletRepository.findByMemberId(memberId)
                        ?: throw WalletException(ErrorCode.NOT_FOUND_WALLET)

                    updatedWallet.balance shouldBe (warmUpCount + measurementCount) * amount
                }
            }
        }
    }

    private data class ChargeLatencyResult(
        val count: Int,
        val avgMs: Double,
        val p50Ms: Double,
        val p95Ms: Double,
        val p99Ms: Double,
        val minMs: Double,
        val maxMs: Double
    ) {
        companion object {
            fun from(durations: List<Long>): ChargeLatencyResult {
                val sorted = durations.sorted()

                return ChargeLatencyResult(
                    count = sorted.size,
                    avgMs = sorted.average() / 1_000_000.0,
                    p50Ms = sorted.percentile(50).toMillis(),
                    p95Ms = sorted.percentile(95).toMillis(),
                    p99Ms = sorted.percentile(99).toMillis(),
                    minMs = sorted.first().toMillis(),
                    maxMs = sorted.last().toMillis()
                )
            }

            private fun List<Long>.percentile(percentile: Int): Long {
                val index = ceil(size * percentile / 100.0).toInt() - 1
                return this[index.coerceIn(0, lastIndex)]
            }

            private fun Long.toMillis(): Double {
                return this / 1_000_000.0
            }
        }
    }

    private fun createWallet(member: Member): Wallet {
        val constructor = Wallet::class.java.constructors
            .firstOrNull { constructor ->
                val parameterTypes = constructor.parameterTypes

                parameterTypes.size == 2 &&
                    (parameterTypes[0] == Member::class.java ||
                        parameterTypes[0].isLongType()) &&
                    parameterTypes[1].isLongType()
            }
            ?: error("Wallet(member/memberId, balance) constructor not found")

        val firstArgument =
            if (constructor.parameterTypes[0] == Member::class.java) {
                member
            } else {
                requireNotNull(member.id)
            }

        return constructor.newInstance(firstArgument, 0L) as Wallet
    }

    private fun Class<*>.isLongType(): Boolean {
        return this == Long::class.java ||
            this == java.lang.Long.TYPE ||
            this.name == "java.lang.Long"
    }
}
