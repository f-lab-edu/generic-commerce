package com.nilgil.commerce.product

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ProductItemOptionElementRepository : JpaRepository<ProductItemOptionElement, Long> {
    @EntityGraph(attributePaths = ["optionElement.option"])
    fun findAllWithOptionDetailsByItem(item: ProductItem): List<ProductItemOptionElement>

    @EntityGraph(attributePaths = ["optionElement.option"])
    fun findAllWithOptionDetailsByItemIn(items: List<ProductItem>): List<ProductItemOptionElement>
}
