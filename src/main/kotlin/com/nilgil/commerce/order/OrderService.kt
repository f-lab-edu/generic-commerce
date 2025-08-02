package com.nilgil.commerce.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderCodeGenerator: OrderCodeGenerator,
    private val orderValidator: OrderValidator,
) {
    @Transactional
    fun createOrder(
        userId: Long,
        request: CreateOrderRequest,
        productItems: List<ProductItemInfo>,
    ): CreateOrderResponse {
        orderValidator.validateOrderItems(request, productItems)
        orderValidator.validateHasActiveOrder(userId)

        val code = orderCodeGenerator.generate()
        val lines = createOrderLines(request, productItems)
        val order = Order(code, lines, userId)

        orderRepository.save(order)

        return CreateOrderResponse(order.code)
    }

    private fun createOrderLines(
        request: CreateOrderRequest,
        productItems: List<ProductItemInfo>,
    ): List<OrderLine> {
        val productItemMap = productItems.associateBy { it.productItemId }
        return request.lines.map {
            val productItem = productItemMap[it.productItemId]!!
            OrderLine.from(productItem, it.quantity)
        }
    }
}
