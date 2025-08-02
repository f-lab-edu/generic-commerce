package com.nilgil.commerce.order

data class CreateOrderRequest(
    val lines: List<CreateOrderLineRequest>,
)

data class CreateOrderLineRequest(
    val productItemId: Long,
    val quantity: Int,
)

data class CreateOrderResponse(
    val code: String,
)

data class ProductItemInfo(
    val title: String,
    val options: Map<String, String>,
    val price: Int,
    val stock: Int,
    val thumbnailImageUrl: String?,
    val productItemId: Long,
)
