package org.jnjeaaaat.openmarket.member.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.jnjeaaaat.openmarket.cart.repository.CartRepository
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.fixture.MemberFixture.member
import org.jnjeaaaat.openmarket.member.fixture.MemberFixture.signUpCommand
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.member.type.MemberType
import org.jnjeaaaat.openmarket.wallet.entity.Wallet
import org.jnjeaaaat.openmarket.wallet.repository.WalletRepository

class SignUpUseCaseTest : FunSpec({

    val memberRepository = mockk<MemberRepository>()
    val cartRepository = mockk<CartRepository>()
    val walletRepository = mockk<WalletRepository>()

    val signUpUseCase = SignUpUseCase(
        memberRepository,
        cartRepository,
        walletRepository,
    )

    beforeTest {
        clearMocks(memberRepository)
    }

    test("이미 존재하는 이메일 예외 발생") {

        // given
        val command = signUpCommand(
            email = "test@email.com"
        )

        every { memberRepository.existsByEmail(any()) } returns true

        // when
        // then
        shouldThrow<MemberException> {
            signUpUseCase(command)
        }.errorCode shouldBe ErrorCode.ALREADY_EXISTS_EMAIL

        verify(exactly = 0) {
            memberRepository.save(any())
        }
    }

    test("유효한 값이 주어지고 Member 저장") {

        // given
        val command = signUpCommand(
            email = "new@email.com"
        )
        val savedMember = member(
            email = command.email,
            name = command.name,
            memberType = MemberType.SELLER
        ).apply { id = 1L }
        val cart = Cart.of(savedMember)
        val wallet = Wallet.of(savedMember)

        every { memberRepository.existsByEmail(any()) } returns false

        // when
        every { memberRepository.save(any()) } returns savedMember
        every { cartRepository.save(any()) } returns cart
        every { walletRepository.save(any()) } returns wallet

        val result = signUpUseCase(command)

        // then
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

        verify(exactly = 1) {
            cartRepository.save(
                match {
                    it.memberId == savedMember.id
                }
            )
        }

        verify(exactly = 1) {
            walletRepository.save(
                match {
                    it.memberId == savedMember.id
                }
            )
        }
    }
})