package com.nilgil.commerce.order

object OrderFixtures {
    fun anOrder(
        code: String = "TEST-ORDER-CODE-12345",
        lines: List<OrderLine> = listOf(anOrderLine()),
        userId: Long = 1L,
    ): Order =
        Order(
            code = code,
            lines = lines,
            userId = userId,
        )

    fun anOrderLine(
        quantity: Int = 1,
        item: OrderItem = anOrderItem(),
    ): OrderLine =
        OrderLine(
            quantity = quantity,
            item = item,
        )

    fun anOrderItem(
        title: String = "테스트 상품",
        options: Map<String, String> = mapOf(Pair("옵션1", "값1"), Pair("옵션2", "값2")),
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
