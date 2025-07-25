package com.nilgil.commerce.product

import com.appmattus.kotlinfixture.kotlinFixture
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ProductImageTest :
    DescribeSpec(
        {

            val fixture = kotlinFixture()

            describe("ProductImage") {

                context("isThumbnailImage") {
                    it("type이 THUMBNAIL일 때 true를 반환한다") {
                        // given
                        val thumbnailImage = fixture.createProductImage(type = ProductImageType.THUMBNAIL)

                        // when, then
                        thumbnailImage.isThumbnailImage() shouldBe true
                    }

                    it("type이 THUMBNAIL이 아닐 때 false를 반환한다") {
                        // given
                        val galleryImage = fixture.createProductImage(type = ProductImageType.GALLERY)

                        // when, then
                        galleryImage.isThumbnailImage() shouldBe false
                    }
                }

                context("isGalleryImage") {
                    it("type이 GALLERY일 때 true를 반환한다") {
                        // given
                        val galleryImage = fixture.createProductImage(type = ProductImageType.GALLERY)

                        // when, then
                        galleryImage.isGalleryImage() shouldBe true
                    }

                    it("type이 GALLERY가 아닐 때 false를 반환한다") {
                        // given
                        val contentImage = fixture.createProductImage(type = ProductImageType.CONTENT)

                        // when, then
                        contentImage.isGalleryImage() shouldBe false
                    }
                }

                context("isContentImage") {
                    it("type이 CONTENT일 때 true를 반환한다") {
                        // given
                        val contentImage = fixture.createProductImage(type = ProductImageType.CONTENT)

                        // when, then
                        contentImage.isContentImage() shouldBe true
                    }

                    it("type이 CONTENT가 아닐 때 false를 반환한다") {
                        // given
                        val thumbnailImage = fixture.createProductImage(type = ProductImageType.THUMBNAIL)

                        // when, then
                        thumbnailImage.isContentImage() shouldBe false
                    }
                }
            }
        },
    )
