package com.nilgil.commerce.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductItemController(
    private val service: ProductItemService,
) {
    @GetMapping("/product-items/{id}")
    fun getProductItem(
        @PathVariable id: Long,
    ): ProductItemResponse = service.getProductItem(id)

    @GetMapping("/product-items")
    fun getProductItems(
        @RequestParam ids: List<Long>,
    ): List<ProductItemResponse> = service.getProductItems(ids)
}
