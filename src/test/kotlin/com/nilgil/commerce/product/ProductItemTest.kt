package com.nilgil.commerce.product

import com.appmattus.kotlinfixture.kotlinFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ProductItemTest :
    DescribeSpec(
        {

            val fixture = kotlinFixture()

            describe("ProductItem") {

                context("create") {
                    listOf(
                        Triple(10000, -10000, "0이면"),
                        Triple(10000, 0, "양수이면"),
                    ).forEach { (basePrice, priceAdjustment, case) ->
                        it("조정된 가격이 $case 정상적으로 생성된다") {
                            // given
                            val product = fixture.createProduct(basePrice = basePrice)

                            // when
                            val productItem =
                                fixture.createProductItem(
                                    priceAdjustment = priceAdjustment,
                                    product = product,
                                )

                            // then
                            productItem.getAdjustedPrice() shouldBe basePrice + priceAdjustment
                        }
                    }

                    it("조정된 가격이 음수이면 IllegalArgumentException이 발생한다") {
                        // given
                        val basePrice = 10000
                        val product = fixture.createProduct(basePrice = basePrice)
                        val priceAdjustment = -20000

                        // when, then
                        shouldThrow<IllegalArgumentException> {
                            fixture.createProductItem(priceAdjustment = priceAdjustment, product = product)
                        }
                    }

                    listOf(
                        0 to "0이면",
                        100 to "양수이면",
                    ).forEach { (stock, case) ->
                        it("재고가 $case 정상적으로 생성된다") {
                            // when
                            val productItem = fixture.createProductItem(stock = stock)

                            // then
                            productItem.stock shouldBe stock
                        }
                    }

                    it("재고가 음수이면 IllegalArgumentException이 발생한다") {
                        // given
                        val stock = -100

                        // when, then
                        shouldThrow<IllegalArgumentException> {
                            fixture.createProductItem(stock = stock)
                        }
                    }
                }

                context("getAdjustedPrice") {
                    it("basePrice와 priceAdjustment를 더한 조정된 가격을 반환한다") {
                        // given
                        val basePrice = 10000
                        val priceAdjustment = -5000
                        val product = fixture.createProduct(basePrice = basePrice)
                        val productItem = fixture.createProductItem(priceAdjustment = priceAdjustment, product = product)

                        // when
                        val adjustedPrice = productItem.getAdjustedPrice()

                        // then
                        adjustedPrice shouldBe basePrice + priceAdjustment
                    }
                }

                context("changePriceAdjustment") {
                    listOf(
                        Triple(10000, -10000, "0이면"),
                        Triple(10000, 0, "양수이면"),
                    ).forEach { (basePrice, priceAdjustment, case) ->
                        it("조정된 가격이 $case 정상 변경된다") {
                            // given
                            val product = fixture.createProduct(basePrice = basePrice)
                            val productItem = fixture.createProductItem(product = product)

                            // when
                            productItem.changePriceAdjustment(priceAdjustment)

                            // then
                            productItem.priceAdjustment shouldBe priceAdjustment
                            productItem.getAdjustedPrice() shouldBe basePrice + priceAdjustment
                        }
                    }

                    it("조정된 가격이 음수이면 IllegalArgumentException이 발생한다") {
                        // given
                        val basePrice = 10000
                        val product = fixture.createProduct(basePrice = basePrice)
                        val productItem = fixture.createProductItem(product = product)
                        val newPriceAdjustment = -20000

                        // when, then
                        shouldThrow<IllegalArgumentException> {
                            productItem.changePriceAdjustment(newPriceAdjustment)
                        }
                    }
                }

                context("decreaseStock") {
                    it("재고보다 적은 양을 감소시키려 하면 정상 감소된다") {
                        // given
                        val stock = 20
                        val productItem = fixture.createProductItem(stock = stock)
                        val decreaseAmount = 5

                        // when
                        productItem.decreaseStock(decreaseAmount)

                        // then
                        productItem.stock shouldBe stock - decreaseAmount
                    }

                    it("재고보다 많은 양을 감소시키려 하면 IllegalArgumentException이 발생한다") {
                        // given
                        val stock = 10
                        val productItem = fixture.createProductItem(stock = stock)
                        val decreaseAmount = 11

                        // when, then
                        shouldThrow<IllegalArgumentException> {
                            productItem.decreaseStock(decreaseAmount)
                        }
                    }
                }

                context("activate") {
                    it("active 상태를 true로 변경한다") {
                        // given
                        val productItem = fixture.createProductItem(active = false)

                        // when
                        productItem.activate()

                        // then
                        productItem.active shouldBe true
                    }
                }

                context("deactivate") {
                    it("active 상태를 false로 변경한다") {
                        // given
                        val productItem = fixture.createProductItem(active = true)

                        // when
                        productItem.deactivate()

                        // then
                        productItem.active shouldBe false
                    }
                }
            }
        },
    )
