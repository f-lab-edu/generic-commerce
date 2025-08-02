package com.nilgil.commerce.order

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable

@Embeddable
data class OrderItem(
    @Column(name = "item_title")
    val title: String,
    @Convert(converter = OrderItemOptionMapConverter::class)
    @Column(name = "item_options")
    val options: Map<String, String> = emptyMap(),
    @Column(name = "item_price")
    val price: Int,
    @Column(name = "item_thumbnail_image_url")
    val thumbnailImageUrl: String? = null,
    @Column(name = "product_item_id")
    val productItemId: Long,
) {
    companion object {
        fun from(itemInfo: ProductItemInfo): OrderItem =
            OrderItem(
                title = itemInfo.title,
                options = itemInfo.options,
                price = itemInfo.price,
                thumbnailImageUrl = itemInfo.thumbnailImageUrl,
                productItemId = itemInfo.productItemId,
            )
    }

    init {
        require(price >= 0) { "가격은 0 이상이어야 합니다." }
    }
}
