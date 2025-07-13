package com.nilgil.commerce.order

import com.nilgil.commerce.common.StringListConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable

@Embeddable
data class OrderItem(
    @Column(name = "item_title")
    val title: String,
    @Convert(converter = StringListConverter::class)
    @Column(name = "item_options")
    val options: List<String> = listOf(),
    @Column(name = "item_price")
    val price: Int,
    @Column(name = "item_thumbnail_image_url")
    val thumbnailImageUrl: String? = null,
    @Column(name = "product_item_id")
    val productItemId: Long,
) {
    init {
        require(price >= 0) { "가격은 0 이상이어야 합니다." }
    }
}
