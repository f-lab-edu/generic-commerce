package com.nilgil.commerce.order

import com.nilgil.commerce.common.LockExecutor
import com.nilgil.commerce.product.ProductItemResponse
import com.nilgil.commerce.product.ProductItemService
import org.springframework.stereotype.Service

@Service
class OrderFacade(
    private val orderService: OrderService,
    private val productItemService: ProductItemService,
    private val lockExecutor: LockExecutor,
) {
    fun createOrder(
        userId: Long,
        request: CreateOrderRequest,
    ): CreateOrderResponse {
        val productItems = getProductItems(request)

        val lockKey = "order:user:$userId"
        return lockExecutor.execute(lockKey) {
            orderService.createOrder(userId, request, productItems)
        }
    }

    private fun getProductItems(request: CreateOrderRequest): List<ProductItemInfo> {
        val itemIds = request.lines.map { it.productItemId }

        return productItemService.getProductItems(itemIds).map {
            it.toInternalItemInfo()
        }
    }
}

private fun ProductItemResponse.toInternalItemInfo() =
    ProductItemInfo(
        title = this.productName,
        options = this.options,
        price = this.price,
        stock = this.stock,
        thumbnailImageUrl = this.thumbnailImageUrl,
        productItemId = this.id,
    )
