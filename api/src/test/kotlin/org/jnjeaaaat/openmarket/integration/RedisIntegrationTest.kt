import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.jnjeaaaat.openmarket.Application
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
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
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("test")
@ImportTestcontainers
class RedisIntegrationTest : BehaviorSpec() {

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
    @Autowired lateinit var categoryCacheRepository: CategoryCacheRepository
    @Autowired lateinit var redisTemplate: RedisTemplate<String, Any>
    @Autowired lateinit var walletChargeUseCase: WalletChargeUseCase
    @Autowired lateinit var walletRepository: WalletRepository

    override fun extensions() = listOf(SpringExtension)

    init {

        beforeContainer {
            redisTemplate.connectionFactory
                ?.connection
                ?.serverCommands()
                ?.flushAll()
        }

        Given("카테고리 트리 저장") {

            val categoryTree = listOf(
                CategoryTreeResult(
                    id = 1L,
                    name = "전자제품",
                    depth = 1
                )
            )

            When("Redis 저장") {

                categoryCacheRepository.saveTree(categoryTree)

                Then("조회 가능") {

                    val result = categoryCacheRepository.getTree()

                    result shouldNotBe null
                    result!!.size shouldBe 1
                    result[0].name shouldBe "전자제품"
                }
            }
        }

        Given("초기 지갑 잔액이 0원일 때") {
            val amount = 10_000L
            val command = ChargeCommand(amount = amount)
            val numberOfThreads = 100

            // Member 연관관계 매핑 상태에 맞게 가짜 멤버 혹은 필요한 데이터 세팅
            val savedMember = memberRepository.save(member())
            walletRepository.save(Wallet.of(member = savedMember))

            When("100개의 스레드가 동시에 10,000원 충전을 요청하면") {
                redisTemplate.delete("wallet:charge:limit:1")
                val executorService = Executors.newFixedThreadPool(numberOfThreads)
                val latch = CountDownLatch(numberOfThreads)

                val successCount = AtomicInteger(0)
                val failCount = AtomicInteger(0)

                repeat(numberOfThreads) {
                    executorService.submit {
                        try {
                            walletChargeUseCase(command, 1L)
                            successCount.incrementAndGet()
                        } catch (e: Exception) {
                            failCount.incrementAndGet()
                        } finally {
                            latch.countDown()
                        }
                    }
                }
                latch.await()
                executorService.shutdown()

                Then("분산락에 의해 최종 잔액은 1_000_000원이어야 한다") {

                    // 최종 지갑 잔액 검증
                    val updatedWallet = walletRepository.findByMemberIdForUpdate(1L)
                        ?: throw WalletException(ErrorCode.NOT_FOUND_WALLET)
                    updatedWallet.balance shouldBe 1_000_000
                }
            }
        }
    }
}