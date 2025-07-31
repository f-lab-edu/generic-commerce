package com.nilgil.commerce.order

import com.nilgil.commerce.product.ProductItemResponse
import com.nilgil.commerce.product.ProductItemService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderCodeGenerator: OrderCodeGenerator,
    private val orderValidator: OrderValidator,
    private val productItemService: ProductItemService,
) {
    @Transactional
    fun createOrder(
        userId: Long,
        request: CreateOrderRequest,
    ): CreateOrderResponse {
        orderValidator.validateHasActiveOrder(userId)

        val orderLines = prepareOrderLines(request)

        val code = orderCodeGenerator.generate()
        val order = Order(code, orderLines, userId)
        orderRepository.save(order)

        return CreateOrderResponse(order.code)
    }

    private fun prepareOrderLines(request: CreateOrderRequest): List<OrderLine> {
        val itemIds = request.lines.map { it.productItemId }
        val itemsMap = productItemService.getProductItems(itemIds).associateBy { it.id }

        orderValidator.validateItems(request, itemsMap)

        return request.lines.map { lineRequest ->
            val productItem = itemsMap[lineRequest.productItemId]!!
            toOrderLine(productItem, lineRequest.quantity)
        }
    }

    private fun toOrderLine(
        item: ProductItemResponse,
        quantity: Int,
    ) = OrderLine(
        OrderItem(
            title = item.productName,
            options = item.options,
            price = item.price,
            thumbnailImageUrl = item.thumbnailImageUrl,
            productItemId = item.id,
        ),
        quantity,
    )
}
