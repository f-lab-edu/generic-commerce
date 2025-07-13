package com.nilgil.commerce.order

import org.mockito.Mockito.mock

object OrderFixtures {
    fun anOrder(
        code: String = "TEST-ORDER-CODE-12345",
        totalAmount: Int = 0,
        userId: Long = 1L,
    ): Order =
        Order(
            code = code,
            totalAmount = totalAmount,
            userId = userId,
        )

    fun anOrderLine(
        order: Order = mock(Order::class.java),
        quantity: Int = 1,
        item: OrderItem = anOrderItem(),
    ): OrderLine =
        OrderLine(
            order = order,
            quantity = quantity,
            item = item,
        )

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
