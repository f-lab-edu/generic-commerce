package com.nilgil.commerce.product

import com.appmattus.kotlinfixture.kotlinFixture
import com.nilgil.commerce.common.error.CoreError
import com.nilgil.commerce.common.error.CoreException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ProductItemServiceTest :
    DescribeSpec({

        val fixture = kotlinFixture()

        val productItemRepository = mockk<ProductItemRepository>()
        val productImageRepository = mockk<ProductImageRepository>()
        val productItemOptionElementRepository = mockk<ProductItemOptionElementRepository>()

        val productItemService =
            ProductItemService(
                productItemRepository = productItemRepository,
                productImageRepository = productImageRepository,
                productItemOptionElementRepository = productItemOptionElementRepository,
            )

        describe("ProductItemService") {

            context("getProductItem") {
                val product = fixture.createProduct()
                val productItemId = 1L
                val productItem = fixture.createProductItem(id = productItemId, product = product)
                val thumbnailUrl = "https://example.com/thumbnail.jpg"
                val thumbnailImage = fixture.createProductImage(url = thumbnailUrl)
                val option1 = fixture.createProductOption(name = "색상")
                val optionElement1 = fixture.createProductOptionElement(option = option1, name = "빨강")
                val itemOptionElement1 = fixture.createProductItemOptionElement(optionElement = optionElement1)
                val option2 = fixture.createProductOption(name = "사이즈")
                val optionElement2 = fixture.createProductOptionElement(option = option2, name = "L")
                val itemOptionElement2 = fixture.createProductItemOptionElement(optionElement = optionElement2)
                val itemOptionElements = listOf(itemOptionElement1, itemOptionElement2)

                every { productItemRepository.findWithProductById(productItemId) } returns productItem
                every { productImageRepository.findByProductAndType(any(), any()) } returns thumbnailImage
                every { productItemOptionElementRepository.findAllWithOptionDetailsByItem(productItem) } returns itemOptionElements

                it("존재하는 상품 아이템 ID로 조회하면, 상품 아이템 정보를 정상 응답한다") {
                    // when
                    val response = productItemService.getProductItem(productItemId)

                    // then
                    response.id shouldBe productItemId
                    response.thumbnailImageUrl shouldBe thumbnailImage.url
                }

                it("존재하지 않는 상품 아이템 ID로 조회하면, NOT_FOUND_ERROR가 발생한다") {
                    // given
                    val invalidId = 99L
                    every { productItemRepository.findWithProductById(invalidId) } returns null

                    // when, then
                    shouldThrow<CoreException> {
                        productItemService.getProductItem(invalidId)
                    }.type shouldBe CoreError.NOT_FOUND_ERROR
                }
            }
        }
    })
