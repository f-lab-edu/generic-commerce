package com.nilgil.commerce.product

import com.appmattus.kotlinfixture.kotlinFixture
import com.nilgil.commerce.common.error.CoreError
import com.nilgil.commerce.common.error.CoreException
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(ProductItemController::class)
class ProductItemControllerTest : DescribeSpec() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var service: ProductItemService

    init {
        val fixture = kotlinFixture()

        describe("ProductItemController") {

            context("GET /product-items/{id}") {

                it("존재하는 상품 아이템 ID로 요청하면, 상품 아이템 정보를 정상 응답한다") {
                    // given
                    val productItemId = 1L
                    val productItem =
                        fixture<ProductItemResponse> {
                            property(ProductItemResponse::id) { productItemId }
                        }
                    every { service.getProductItem(productItemId) } returns productItem

                    // when, then
                    mockMvc
                        .get("/product-items/$productItemId")
                        .andExpect {
                            status { isOk() }
                            jsonPath("$.id") { value(productItem.id) }
                            jsonPath("$.productName") { value(productItem.productName) }
                            jsonPath("$.options") { value(productItem.options) }
                            jsonPath("$.thumbnailImageUrl") { value(productItem.thumbnailImageUrl) }
                            jsonPath("$.price") { value(productItem.price) }
                            jsonPath("$.stock") { value(productItem.stock) }
                        }
                }

                it("존재하지 않는 상품 아이템 ID로 요청하면, 404 상태 코드를 응답한다") {
                    // given
                    val productItemId = 1L
                    every { service.getProductItem(productItemId) } throws CoreException(CoreError.NOT_FOUND_ERROR)

                    // when, then
                    mockMvc
                        .get("/product-items/$productItemId")
                        .andExpect { status { isNotFound() } }
                }
            }

            context("GET /product-items") {
                it("여러 상품 아이템 ID를 요청하면, 순서대로 정상 응답한다") {
                    // given
                    val productItemIds = listOf(10L, 3L, 5L)
                    val productItems =
                        productItemIds.map {
                            fixture<ProductItemResponse> {
                                property(ProductItemResponse::id) { it }
                            }
                        }
                    every { service.getProductItems(productItemIds) } returns productItems

                    // when, then
                    mockMvc
                        .get("/product-items") {
                            param("ids", productItemIds.joinToString(","))
                        }.andExpect {
                            status { isOk() }
                            jsonPath("$.length()") { value(productItems.size) }
                            jsonPath("$[0].id") { value(productItemIds[0]) }
                            jsonPath("$[1].id") { value(productItemIds[1]) }
                            jsonPath("$[2].id") { value(productItemIds[2]) }
                        }
                }

                it("ids 파라미터가 비어있는 경우, 빈 목록을 응답한다") {
                    // given
                    val emptyIds = emptyList<Long>()
                    every { service.getProductItems(emptyIds) } returns emptyList()

                    // when, then
                    mockMvc
                        .get("/product-items") {
                            param("ids", "")
                        }.andExpect {
                            status { isOk() }
                            content { json("[]") }
                        }
                }

                it("ids 파라미터가 없는 경우, 400 상태 코드를 응답한다") {
                    // when, then
                    mockMvc
                        .get("/product-items")
                        .andExpect {
                            status { isBadRequest() }
                        }
                }
            }
        }
    }
}
