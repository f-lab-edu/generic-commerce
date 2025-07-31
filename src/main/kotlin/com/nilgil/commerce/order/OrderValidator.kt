package com.nilgil.commerce.order

import com.nilgil.commerce.common.error.CoreException
import com.nilgil.commerce.product.ProductItemResponse
import org.springframework.stereotype.Component

@Component
class OrderValidator(
    private val orderRepository: OrderRepository,
) {
    fun validateHasActiveOrder(userId: Long) {
        val activeOrder = orderRepository.findByUserIdAndStatusIn(userId, OrderStatus.activeStatuses)

        if (activeOrder != null) {
            throw CoreException(OrderError.ALREADY_HAS_ACTIVE_ORDER, detail = "code: ${activeOrder.code}")
        }
    }

    fun validateItems(
        request: CreateOrderRequest,
        itemsMap: Map<Long, ProductItemResponse>,
    ) {
        val invalidItems =
            request.lines.mapNotNull { line ->
                checkAndGetInvalid(line, itemsMap)
            }

        if (invalidItems.isNotEmpty()) {
            throw CoreException(OrderError.INVALID_ITEM, detail = invalidItems)
        }
    }

    private fun checkAndGetInvalid(
        line: CreateOrderLineRequest,
        itemsMap: Map<Long, ProductItemResponse>,
    ): InvalidLineInfo? {
        val item = itemsMap[line.productItemId]
        return when {
            item == null ->
                InvalidLineInfo(line.productItemId, InvalidType.NOT_FOUND)

            line.quantity > item.stock ->
                InvalidLineInfo(line.productItemId, InvalidType.STOCK_NOT_ENOUGH)

            else -> null
        }
    }
}
