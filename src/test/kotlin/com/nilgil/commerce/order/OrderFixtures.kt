package com.nilgil.commerce.order

object OrderFixtures {
    fun anOrderItem(
        title: String = "테스트 상품",
        options: List<String> = listOf("옵션1", "옵션2"),
        price: Int = 10000,
        productItemId: Long = 1L,
    ): OrderItem =
        OrderItem(
            title = title,
            options = options,
            price = price,
            productItemId = productItemId,
            thumbnailImageUrl = "http://test.image/1",
        )
}
