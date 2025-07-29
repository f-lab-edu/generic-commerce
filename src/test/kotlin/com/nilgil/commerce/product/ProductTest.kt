package com.nilgil.commerce.product

import com.appmattus.kotlinfixture.kotlinFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ProductTest :
    DescribeSpec(
        {

            val fixture = kotlinFixture()

            describe("Product") {

                context("create") {
                    listOf(
                        0 to "0이면",
                        10000 to "양수이면",
                    ).forEach { (basePrice, case) ->
                        it("basePrice가 $case 정상 생성된다") {
                            // when
                            val product = fixture.createProduct(basePrice = basePrice)

                            // then
                            product.basePrice shouldBe basePrice
                        }
                    }

                    it("basePrice가 음수이면 IllegalArgumentException이 발생한다") {
                        // given
                        val basePrice = -10000

                        // when, then
                        shouldThrow<IllegalArgumentException> {
                            fixture.createProduct(basePrice = basePrice)
                        }
                    }
                }

                context("changeName") {
                    it("상품 이름이 정상 변경된다") {
                        // given
                        val product = fixture.createProduct()
                        val name = "new name"

                        // when
                        product.changeName(name)

                        // then
                        product.name shouldBe name
                    }
                }

                context("changeDescription") {
                    it("상품 설명이 정상 변경된다") {
                        // given
                        val product = fixture.createProduct()
                        val description = "new description"

                        // when
                        product.changeDescription(description)

                        // then
                        product.description shouldBe description
                    }
                }

                context("changeBasePrice") {
                    listOf(
                        0 to "0이면",
                        10000 to "양수이면",
                    ).forEach { (basePrice, case) ->
                        it("변경하려는 가격이 $case 정상 변경된다") {
                            // given
                            val product = fixture.createProduct()

                            // when
                            product.changeBasePrice(basePrice)

                            // then
                            product.basePrice shouldBe basePrice
                        }
                    }

                    it("변경하려는 가격이 음수이면 IllegalArgumentException이 발생한다") {
                        // given
                        val product = fixture.createProduct()
                        val basePrice = -10000

                        // when, then
                        shouldThrow<IllegalArgumentException> {
                            product.changeBasePrice(basePrice)
                        }
                    }
                }

                context("activate") {
                    it("active 상태를 true로 변경한다") {
                        // given
                        val product = fixture.createProduct(active = false)

                        // when
                        product.activate()

                        // then
                        product.active shouldBe true
                    }
                }

                context("deactivate") {
                    it("active 상태를 false로 변경한다") {
                        // given
                        val product = fixture.createProduct(active = true)

                        // when
                        product.deactivate()

                        // then
                        product.active shouldBe false
                    }
                }
            }
        },
    )
