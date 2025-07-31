package com.nilgil.commerce.order

data class CreateOrderRequest(
    val lines: List<CreateOrderLineRequest> = listOf(),
) {
    init {
        val distinctItemIds = lines.distinctBy { it.productItemId }
        require(lines.size == distinctItemIds.size) { "주문 항목에 중복된 상품이 존재할 수 없습니다." }
    }
}

data class CreateOrderLineRequest(
    val productItemId: Long,
    val quantity: Int,
)

data class CreateOrderResponse(
    val code: String,
)
