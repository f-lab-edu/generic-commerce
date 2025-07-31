package com.nilgil.commerce.product

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ProductItemRepository : JpaRepository<ProductItem, Long> {
    @EntityGraph(attributePaths = ["product"])
    fun findWithProductById(productItemId: Long): ProductItem?

    @EntityGraph(attributePaths = ["product"])
    fun findWithProductByIdIn(productItemIds: List<Long>): List<ProductItem>
}
