package org.jnjeaaaat.openmarket.member.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.member.event.MemberRegisteredEvent
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.fixture.MemberFixture
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.member.type.MemberType
import org.springframework.context.ApplicationEventPublisher

class SignUpUseCaseTest : BehaviorSpec({

    val memberRepository = mockk<MemberRepository>()
    val publisher = mockk<ApplicationEventPublisher>(relaxed = true)

    val signUpUseCase = SignUpUseCase(
        memberRepository,
        publisher
    )

    Given("이미 존재하는 이메일로") {

        val command = MemberFixture.signUpCommand(
            email = "test@email.com"
        )

        every { memberRepository.existsByEmail(any()) } returns true

        When("회원가입 요청하면") {
            Then("예외가 발생한다") {
                shouldThrow<MemberException> {
                    signUpUseCase(command)
                }.errorCode shouldBe ErrorCode.ALREADY_EXISTS_EMAIL
            }

            Then("회원은 저장되지 않는다") {
                verify(exactly = 0) {
                    memberRepository.save(any())
                }
            }
        }
    }

    Given("유효한 값이 주어지고") {
        val command = MemberFixture.signUpCommand(
            email = "new@email.com"
        )
        val savedMember = MemberFixture.createMember(
            email = command.email,
            name = command.name,
            memberType = MemberType.SELLER
        ).apply { id = 1L }

        every { memberRepository.existsByEmail(any()) } returns false

        When("회원가입 요청하면") {

            every { memberRepository.save(any()) } returns savedMember
            every { publisher.publishEvent(any()) } returns Unit

            val result = signUpUseCase(command)

            Then("회원이 저장된다") {
                result shouldBe 1L

                verify(exactly = 1) {
                    memberRepository.existsByEmail(command.email)
                }

                verify(exactly = 1) {
                    memberRepository.save(
                        match {
                            it.email == command.email &&
                                    it.name == command.name
                        }
                    )
                }
            }

            Then("회원가입 이벤트를 발행한다") {
                verify(exactly = 1) {
                    publisher.publishEvent(
                        ofType(MemberRegisteredEvent::class)
                    )
                }
            }
        }
    }
})