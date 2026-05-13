import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.jnjeaaaat.openmarket.Application
import org.jnjeaaaat.openmarket.category.command.CategoryTreeResult
import org.jnjeaaaat.openmarket.category.repository.CategoryCacheRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

@SpringBootTest(classes = [Application::class])
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

    @Autowired
    lateinit var categoryCacheRepository: CategoryCacheRepository

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

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
    }
}