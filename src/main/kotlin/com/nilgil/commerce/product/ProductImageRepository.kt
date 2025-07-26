package com.nilgil.commerce.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageRepository : JpaRepository<ProductImage, Long> {
    fun findByProductAndType(
        product: Product,
        type: ProductImageType,
    ): ProductImage?

    fun findAllByProductInAndType(
        products: List<Product>,
        type: ProductImageType,
    ): List<ProductImage>
}
