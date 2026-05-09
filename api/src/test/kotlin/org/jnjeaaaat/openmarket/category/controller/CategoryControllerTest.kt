package org.jnjeaaaat.openmarket.category.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.category.exception.CategoryException
import org.jnjeaaaat.openmarket.category.fixture.CategoryFixture.addCategoryRequest
import org.jnjeaaaat.openmarket.category.fixture.CategoryFixture.addCategoryResult
import org.jnjeaaaat.openmarket.category.fixture.CategoryFixture.childCategory
import org.jnjeaaaat.openmarket.category.fixture.CategoryFixture.getCategoryResult
import org.jnjeaaaat.openmarket.category.usecase.AddCategoryUseCase
import org.jnjeaaaat.openmarket.category.usecase.GetCategoryUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@WebMvcTest(CategoryController::class)
@AutoConfigureMockMvc
class CategoryControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) : DescribeSpec() {

    @MockkBean
    lateinit var addCategoryUseCase: AddCategoryUseCase

    @MockkBean
    lateinit var getCategoryUseCase: GetCategoryUseCase

    override fun extensions() = listOf(SpringExtension)

    init {
        describe("/categories 으로 post 요청을 했을때") {

            val url = "/categories"

            context("카테고리 생성 요청이 전달 되면") {

                it("200 상태코드를 반환한다.") {

                    every {
                        addCategoryUseCase(any())
                    } returns addCategoryResult()

                    val requestBody =
                        objectMapper.writeValueAsString(addCategoryRequest())

                    mockMvc.post(url) {
                        contentType = MediaType.APPLICATION_JSON
                        content = requestBody
                    }
                        .andExpect {
                            status { isOk() }
                            jsonPath("$.id").value(1L)
                            jsonPath("$.name").value("전자기기")
                            jsonPath("$.depth").value(1)
                            jsonPath("$.sortOrder").value(1)
                        }
                        .andDo { print() }
                }
            }

            context("존재하지 않는 부모 카테고리면") {

                it("404 상태코드를 반환한다.") {

                    every {
                        addCategoryUseCase(any())
                    } throws CategoryException(
                        ErrorCode.NOT_FOUND_CATEGORY
                    )

                    val requestBody = objectMapper.writeValueAsString(addCategoryRequest())

                    mockMvc.post(url) {
                        contentType = MediaType.APPLICATION_JSON
                        content = requestBody
                    }
                        .andExpect {
                            status { isNotFound() }
                        }
                        .andDo { print() }
                }
            }

            context("이미 존재하는 카테고리면") {

                it("400 상태코드를 반환한다.") {

                    every {
                        addCategoryUseCase(any())
                    } throws CategoryException(
                        ErrorCode.ALREADY_EXISTS_CATEGORY
                    )

                    val requestBody =
                        objectMapper.writeValueAsString(addCategoryRequest())

                    mockMvc.post(url) {
                        contentType = MediaType.APPLICATION_JSON
                        content = requestBody
                    }
                        .andExpect {
                            status { isBadRequest() }
                        }
                        .andDo { print() }
                }
            }
        }

        describe("/categories/{categoryId} 로 get 요청을 했을때") {

            val url = "/categories/{categoryId}"

            context("정상적인 요청이라면") {

                it("200 상태코드와 category 정보를 반환한다.") {

                    val childCategory1 = childCategory(
                        id = 2L,
                        name = "상의",
                        sortOrder = 1
                    )
                    val childCategory2 = childCategory(
                        id = 3L,
                        name = "하의",
                        sortOrder = 2
                    )
                    val children = listOf(childCategory1, childCategory2)

                    every {
                        getCategoryUseCase(any())
                    } returns getCategoryResult(
                        children = children
                    )

                    mockMvc.get(url, 1L) {
                        contentType = MediaType.APPLICATION_JSON
                    }
                        .andExpect {
                            status { isOk() }
                            jsonPath("$.id").value(1L)
                            jsonPath("$.name").value("옷")
                            jsonPath("$.depth").value(1)
                            jsonPath("$.sortOrder").value(1)
                            jsonPath("$.children[0].id").value(2L)
                            jsonPath("$.children[0].name").value("상의")
                            jsonPath("$.isVisible").value(true)
                        }
                        .andDo { print() }

                }
            }

            context("존재하지 않는 카테고리 라면") {

                it("404 상태코드를 반환한다.") {
                    every {
                        getCategoryUseCase(any())
                    } throws CategoryException(
                        ErrorCode.NOT_FOUND_CATEGORY
                    )

                    mockMvc.get(url, 1L) {
                        contentType = MediaType.APPLICATION_JSON
                    }
                        .andExpect {
                            status { isNotFound() }
                        }
                        .andDo { print() }
                }
            }
        }
    }
}
