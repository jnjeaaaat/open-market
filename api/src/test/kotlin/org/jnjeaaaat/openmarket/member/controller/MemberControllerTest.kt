package org.jnjeaaaat.openmarket.member.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import org.jnjeaaaat.openmarket.member.fixture.MemberFixture
import org.jnjeaaaat.openmarket.member.usecase.SignUpUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(MemberController::class)
@AutoConfigureMockMvc
class MemberControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) : DescribeSpec() {

    @MockkBean
    lateinit var signUpUseCase: SignUpUseCase

    override fun extensions() = listOf(SpringExtension)

    init {
        describe("/members/sign-up 으로 post 요청을 했을때") {
            val url = "/members/sign-up"

            context("회원가입 요청이 전달 되면") {
                val signUpRequest = MemberFixture.signUpRequest()

                every { signUpUseCase(any()) } returns 1L

                val requestBody = objectMapper.writeValueAsString(signUpRequest)

                it("201 상태코드를 반환한다.") {

                    mockMvc.perform(
                        post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                    )
                        .andExpect(status().`is`(201))
                        .andDo(print())
                }
            }
        }
    }

}