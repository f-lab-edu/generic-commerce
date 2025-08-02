package com.nilgil.commerce.order

import com.nilgil.commerce.common.error.CoreException
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

    fun validateOrderItems(
        request: CreateOrderRequest,
        productItems: List<ProductItemInfo>,
    ) {
        val itemInfoMap = productItems.associateBy { it.productItemId }

        val invalidItems =
            request.lines.mapNotNull { lineRequest ->
                getInvalidOrNull(lineRequest, itemInfoMap)
            }

        if (invalidItems.isNotEmpty()) {
            throw CoreException(OrderError.INVALID_ITEM, detail = invalidItems)
        }
    }

    private fun getInvalidOrNull(
        line: CreateOrderLineRequest,
        itemsMap: Map<Long, ProductItemInfo>,
    ): InvalidLineInfo? {
        val item = itemsMap[line.productItemId]
        return when {
            item == null ->
                InvalidLineInfo(line.productItemId, InvalidType.NOT_FOUND)

            item.stock < line.quantity ->
                InvalidLineInfo(line.productItemId, InvalidType.STOCK_NOT_ENOUGH)

            else -> null
        }
    }
}
